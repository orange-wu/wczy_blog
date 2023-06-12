package com.my9z.blog.service.auth.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my9z.blog.common.constant.RedisKeyConstant;
import com.my9z.blog.common.enums.ErrorCodeEnum;
import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.dto.RoleAuthDto;
import com.my9z.blog.common.pojo.dto.RoleIdDto;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
import com.my9z.blog.common.pojo.req.SaveRoleReq;
import com.my9z.blog.common.pojo.req.SearchRoleReq;
import com.my9z.blog.common.pojo.req.UpdateRoleReq;
import com.my9z.blog.common.pojo.resp.RoleResp;
import com.my9z.blog.common.util.PageUtil;
import com.my9z.blog.common.util.UserUtil;
import com.my9z.blog.mapper.RoleMapper;
import com.my9z.blog.mapper.UserAuthMapper;
import com.my9z.blog.service.auth.RoleService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 角色service实现类
 * @author: wczy9
 * @createTime: 2023-01-29  21:26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public WPage<RoleResp> listRoles(SearchRoleReq searchRoleReq) {
        Page<RoleResp> page = new Page<>(searchRoleReq.getPageNumber(), searchRoleReq.getPageSize());
        //分页模糊查询
        Page<RoleResp> roleRespPage = baseMapper.roleRespPage(page, searchRoleReq.getRoleName());
        return PageUtil.convert(roleRespPage);
    }

    @Override
    public void saveRole(SaveRoleReq saveRoleReq) {
        //判断角色名和权限名唯一
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<RoleEntity>()
                .eq(RoleEntity::getRoleName, saveRoleReq.getRoleName())
                .or()
                .eq(RoleEntity::getRoleLabel, saveRoleReq.getRoleLabel()));
        if (count > BigDecimal.ZERO.intValue()) {
            throw ErrorCodeEnum.ROLE_NAME_OR_LABEL_ALREADY_EXIST.buildException();
        }
        //直接新增角色
        RoleEntity roleEntity = BeanUtil.copyProperties(saveRoleReq, RoleEntity.class);
        baseMapper.insert(roleEntity);
    }

    @Override
    public void updateRole(UpdateRoleReq updateRoleReq) {
        //判断角色名唯一
        RoleEntity roleEntity = baseMapper.selectOne(new LambdaQueryWrapper<RoleEntity>()
                .eq(RoleEntity::getRoleName, updateRoleReq.getRoleName()));
        if (roleEntity != null && !ObjectUtil.equals(roleEntity.getId(), updateRoleReq.getId())) {
            throw ErrorCodeEnum.ROLE_NAME_ALREADY_EXIST.buildException();
        }
        //找到现有角色数据
        RoleEntity oldRole = baseMapper.selectById(updateRoleReq.getId());
        if (oldRole == null) {
            throw ErrorCodeEnum.ROLE_DATA_IS_NOT_EXIST.buildException();
        }
        //构建修改的角色数据
        RoleEntity newRole = BeanUtil.copyProperties(updateRoleReq, RoleEntity.class);
        //菜单权限是否修改
        boolean menuUpdate = ObjectUtil.equals(newRole.getMenuIds(), oldRole.getMenuIds());
        //接口资源权限是否修改
        boolean resourceUpdate = ObjectUtil.equals(newRole.getResourceIds(), oldRole.getResourceIds());
        //角色是否从启用修改为禁用
        boolean disableUpdate = oldRole.getDisable() == Boolean.FALSE && newRole.getDisable() == Boolean.TRUE;
        //五个字段其中一个出现修改则进行更改
        if (StrUtil.equals(newRole.getRoleName(), oldRole.getRoleName())
                || StrUtil.equals(newRole.getRoleLabel(), oldRole.getRoleLabel())
                || menuUpdate || resourceUpdate || disableUpdate) {
            baseMapper.updateById(newRole);
        }
        //当菜单权限或者接口权限出现变更，或者角色从启用-->禁用，需要把对应的用户强制下线
        if (menuUpdate || resourceUpdate || disableUpdate) {
            //找到当前角色的用户
            List<UserAuthEntity> userAuthEntities = userAuthMapper.selectUsrByRoleId(newRole.getId());
            List<Long> userIdList = userAuthEntities.stream()
                    .map(UserAuthEntity::getId)
                    .collect(Collectors.toList());
            //强制下线该用户
            UserUtil.logout(userIdList);
            //删除相关用户权限缓存
            deleteUserAuthCache(resourceUpdate, disableUpdate, userIdList);
        }
    }

    @Override
    public void deleteRoleById(Long roleId) {
        //查询当前角色下是否存在用户
        List<UserAuthEntity> userAuthEntities = userAuthMapper.selectUsrByRoleId(roleId);
        if (CollUtil.isNotEmpty(userAuthEntities)) {
            throw ErrorCodeEnum.ROLE_USER_EXIST.buildException();
        }
        baseMapper.deleteById(roleId);
    }

    @Override
    public List<RoleIdDto> listEnableRoleNameAndId() {
        List<RoleEntity> roleEntities = baseMapper.selectList(new LambdaQueryWrapper<RoleEntity>()
                .eq(RoleEntity::getDisable, Boolean.FALSE)
                .select(RoleEntity::getId, RoleEntity::getRoleName));
        if (CollUtil.isEmpty(roleEntities)) return null;
        //对象转换
        return new ArrayList<>(CollUtil.trans(roleEntities, (roleEntity) -> {
            RoleIdDto roleIdDto = new RoleIdDto();
            roleIdDto.setId(roleEntity.getId());
            roleIdDto.setRoleName(roleEntity.getRoleName());
            return roleIdDto;
        }));
    }

    @Override
    public List<RoleAuthDto> roleAuthList() {
        return baseMapper.roleAuthList();
    }

    /**
     * 删除用户的接口资源和用户权限缓存
     *
     * @param resourceUpdate 是否修改接口资源
     * @param disableUpdate  是否禁用用户
     * @param userIdList     用户id集合
     */
    private void deleteUserAuthCache(boolean resourceUpdate, boolean disableUpdate, List<Long> userIdList) {
        //用户接口权限缓存删除
        if (resourceUpdate) {
            String userPermissionKey = RedisKeyConstant.getUserPermissionKey();
            RMap<Long, List<String>> userPermissionCache = redissonClient.getMap(userPermissionKey);
            userPermissionCache.fastRemove(ArrayUtil.toArray(userIdList, Long.class));
        }
        //用户角色缓存删除
        if (disableUpdate) {
            String userRoleKey = RedisKeyConstant.getUserRoleKey();
            RMap<Long, List<String>> userRoleCache = redissonClient.getMap(userRoleKey);
            userRoleCache.fastRemove(ArrayUtil.toArray(userIdList, Long.class));
        }
    }

}