package com.my9z.blog.service.auth.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my9z.blog.common.enums.ErrorCodeEnum;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
import com.my9z.blog.common.pojo.req.SaveRoleReq;
import com.my9z.blog.common.pojo.req.SearchRoleReq;
import com.my9z.blog.common.pojo.req.UpdateRoleReq;
import com.my9z.blog.common.pojo.resp.RoleResp;
import com.my9z.blog.common.util.UserUtil;
import com.my9z.blog.mapper.RoleMapper;
import com.my9z.blog.mapper.UserAuthMapper;
import com.my9z.blog.service.auth.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private UserAuthMapper userAuthMapper;

    @Override
    public Page<RoleResp> listRoles(SearchRoleReq searchRoleReq) {
        Page<RoleResp> page = new Page<>(searchRoleReq.getPageNumber(), searchRoleReq.getPageSize());
        //分页模糊查询
        return baseMapper.roleRespPage(page, searchRoleReq.getRoleName());
    }

    @Override
    public void saveRole(SaveRoleReq saveRoleReq) {
        //判断角色名唯一
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<RoleEntity>()
                .eq(RoleEntity::getRoleName, saveRoleReq.getRoleName()));
        if (count > BigDecimal.ZERO.intValue()) {
            throw ErrorCodeEnum.ROLE_NAME_ALREADY_EXIST.buildException();
        }
        //直接新增角色
        RoleEntity roleEntity = BeanUtil.copyProperties(saveRoleReq, RoleEntity.class);
        baseMapper.insert(roleEntity);
    }

    @Override
    public void updateRole(UpdateRoleReq updateRoleReq) {
        //判断角色名和权限名分别唯一
        RoleEntity roleEntity = baseMapper.selectOne(new LambdaQueryWrapper<RoleEntity>()
                .eq(RoleEntity::getRoleName, updateRoleReq.getRoleName()));
        if (roleEntity != null && !ObjectUtil.equals(roleEntity.getId(), updateRoleReq.getId())) {
            throw ErrorCodeEnum.ROLE_NAME_ALREADY_EXIST.buildException();
        }
        //修改对应角色
        RoleEntity oldRole = baseMapper.selectById(updateRoleReq.getId());
        if (oldRole == null) {
            throw ErrorCodeEnum.ROLE_DATA_IS_NOT_EXIST.buildException();
        }
        RoleEntity newRole = BeanUtil.copyProperties(updateRoleReq, RoleEntity.class);
        baseMapper.updateById(newRole);
        //修改角色后判断接口和资源权限是否更改
        if (ObjectUtil.equals(newRole.getMenuIds(), oldRole.getMenuIds()) ||
                ObjectUtil.equals(newRole.getResourceIds(), oldRole.getResourceIds())) {
            //找到当前角色的用户
            List<UserAuthEntity> userAuthEntities = userAuthMapper.selectUsrByRoleId(newRole.getId());
            List<Long> userIdList = userAuthEntities.stream()
                    .map(UserAuthEntity::getId)
                    .collect(Collectors.toList());
            //强制下线该用户
            UserUtil.logout(userIdList);
        }
    }

}