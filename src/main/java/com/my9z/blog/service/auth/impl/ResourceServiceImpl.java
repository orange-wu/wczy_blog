package com.my9z.blog.service.auth.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my9z.blog.common.enums.ErrorCodeEnum;
import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.entity.auth.ResourceEntity;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
import com.my9z.blog.common.pojo.req.SaveOrUpdateResourceReq;
import com.my9z.blog.common.pojo.req.SearchResourceReq;
import com.my9z.blog.common.pojo.resp.ModularResp;
import com.my9z.blog.common.pojo.resp.ResourceResp;
import com.my9z.blog.common.pojo.resp.ResourceTreeResp;
import com.my9z.blog.mapper.ResourceMapper;
import com.my9z.blog.mapper.RoleMapper;
import com.my9z.blog.service.auth.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public WPage<ResourceResp> listResources(SearchResourceReq searchResourceReq) {
        String parentId = searchResourceReq.getParentId();
        //解析模块id集合
        List<String> parentIdStrList = StrUtil.split(parentId, StrPool.COMMA);
        List<Long> parentIdList = parentIdStrList.stream()
                .filter(StrUtil::isNotBlank)
                .map(NumberUtil::parseLong).collect(Collectors.toList());
        //根据模块id集合分页查询接口资源
        Page<ResourceResp> page = new Page<>(searchResourceReq.getPageNumber(), searchResourceReq.getPageSize());
        Page<ResourceResp> resourceRespPage = baseMapper.resourceRespPage(page, parentIdList);
        return new WPage<>(resourceRespPage.getCurrent(), resourceRespPage.getSize(),
                resourceRespPage.getTotal(), resourceRespPage.getRecords());
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
        //组装resourceEntity数据库对象，单独查询模块名字段
        ResourceEntity resourceEntity = BeanUtil.copyProperties(resourceReq, ResourceEntity.class);
        ResourceEntity modular = baseMapper.selectOne(new LambdaQueryWrapper<ResourceEntity>()
                .eq(ResourceEntity::getId, resourceEntity.getParentId())
                .eq(ResourceEntity::getModular, Boolean.TRUE));
        resourceEntity.setModularName(modular.getModularName());
        if (resourceEntity.getId() != null) {
            //permission字段不支持修改
            resourceEntity.setPermission(null);
        }
        //修改或新增
        this.saveOrUpdate(resourceEntity);
    }

    @Override
    public List<ResourceTreeResp> listModularResource() {
        //查询接口模块信息以及不支持匿名访问的接口资源
        List<ResourceEntity> resourceEntityList = baseMapper.selectList(new LambdaQueryWrapper<ResourceEntity>()
                .select(ResourceEntity::getId, ResourceEntity::getResourceName, ResourceEntity::getModularName, ResourceEntity::getParentId)
                .eq(ResourceEntity::getAnonymous, Boolean.FALSE)
                .or()
                .isNull(ResourceEntity::getAnonymous));
        if (CollUtil.isEmpty(resourceEntityList)) return null;
        //获取所有接口模块信息
        List<ResourceEntity> modilarList = resourceEntityList.stream()
                .filter(modular -> modular.getParentId() == null)
                .collect(Collectors.toList());
        //根据接口模块分组
        Map<Long, List<ResourceEntity>> resourceMapByParentId = resourceEntityList.stream()
                .filter(resource -> resource.getParentId() != null)
                .collect(Collectors.groupingBy(ResourceEntity::getParentId));
        //组装树形父子结构
        List<ResourceTreeResp> resourceTreeRespList = new ArrayList<>();
        modilarList.forEach(modular -> {
            List<ResourceEntity> resourceEntities = resourceMapByParentId.get(modular.getId());
            List<ResourceTreeResp> resourceList = new ArrayList<>();
            if (CollUtil.isNotEmpty(resourceEntities)) {
                resourceList = resourceEntities.stream()
                        .map(resource ->
                                new ResourceTreeResp(resource.getId(), resource.getResourceName(), null))
                        .collect(Collectors.toList());
            }
            ResourceTreeResp resourceTreeResp = new ResourceTreeResp(modular.getId(), modular.getModularName(), resourceList);
            resourceTreeRespList.add(resourceTreeResp);
        });
        return resourceTreeRespList;
    }

}
