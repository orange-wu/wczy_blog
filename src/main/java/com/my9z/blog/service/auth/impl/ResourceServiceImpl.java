package com.my9z.blog.service.auth.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my9z.blog.common.enums.ErrorCodeEnum;
import com.my9z.blog.common.pojo.entity.auth.ResourceEntity;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
import com.my9z.blog.common.pojo.req.SaveOrUpdateResourceReq;
import com.my9z.blog.common.pojo.resq.ModularResp;
import com.my9z.blog.common.pojo.resq.ResourceResp;
import com.my9z.blog.mapper.ResourceMapper;
import com.my9z.blog.mapper.RoleMapper;
import com.my9z.blog.service.auth.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 菜单资源service实现类
 * @author: kim
 * @createTime: 2023-02-02  16:40
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, ResourceEntity> implements ResourceService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<ResourceResp> listResources(String parentId) {
        //解析模块id集合
        List<String> parentIdStrList = StrUtil.split(parentId, StrPool.COMMA);
        List<Long> parentIdList = parentIdStrList.stream()
                .filter(StrUtil::isNotBlank)
                .map(NumberUtil::parseLong).collect(Collectors.toList());
        //根据模块id集合查询接口资源
        List<ResourceEntity> resourceEntityList = baseMapper.selectList(new LambdaQueryWrapper<ResourceEntity>()
                .in(CollUtil.isNotEmpty(parentIdList), ResourceEntity::getParentId, parentIdList)
                .eq(ResourceEntity::getModular, Boolean.FALSE)
                .orderByAsc(ResourceEntity::getParentId));
        return BeanUtil.copyToList(resourceEntityList, ResourceResp.class);
    }

    @Override
    public List<ModularResp> listModular() {
        List<ResourceEntity> resourceEntityList = baseMapper.selectList(new LambdaQueryWrapper<ResourceEntity>()
                .eq(ResourceEntity::getModular, Boolean.TRUE));
        return resourceEntityList.stream()
                .map(resource -> {
                    ModularResp modularResp = new ModularResp();
                    modularResp.setModularId(resource.getId());
                    modularResp.setModularValue(resource.getModularName());
                    return modularResp;
                }).collect(Collectors.toList());
    }

    @Override
    public void deleteResource(Long resourceId) {
        //查询该接口是否有角色关联
        List<RoleEntity> roleEntityList = roleMapper.selectCountResourceId(resourceId);
        if (CollUtil.isNotEmpty(roleEntityList)) {
            //当前接口存在角色关联，不允许删除
            String roleName = roleEntityList.stream()
                    .map(RoleEntity::getRoleName)
                    .collect(Collectors.joining(StrPool.COMMA, StrPool.BRACKET_START, StrPool.BRACKET_END));
            throw ErrorCodeEnum.RESOURCE_IS_USED_BY_ROLE.buildException(roleName);
        }
        //直接删除对应接口资源，暂时不考虑接口模块。// TODO: 2023/2/7 后续再看是否需要管理接口模块
        baseMapper.deleteById(resourceId);
    }

    @Override
    public void saveOrUpdateResource(SaveOrUpdateResourceReq resourceReq) {
        //组装resourceEntity数据库对象，单独查询模块名字段 FIXME: 2023/2/8 有优化空间，这次查询可以具体新增时省略掉
        ResourceEntity resourceEntity = BeanUtil.copyProperties(resourceReq, ResourceEntity.class);
        ResourceEntity modular = baseMapper.selectOne(new LambdaQueryWrapper<ResourceEntity>()
                .eq(ResourceEntity::getId, resourceEntity.getParentId())
                .eq(ResourceEntity::getModular, Boolean.TRUE));
        resourceEntity.setModularName(modular.getModularName());
        //修改或新增
        this.saveOrUpdate(resourceEntity);
        // TODO: 2023/2/8 弄权限之后这个地方是否需要触发些什么
    }

}
