package com.my9z.blog.service.auth.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my9z.blog.common.pojo.entity.auth.ResourceEntity;
import com.my9z.blog.common.pojo.resq.ResourceResp;
import com.my9z.blog.mapper.ResourceMapper;
import com.my9z.blog.service.auth.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @description: 菜单资源service实现类
 * @author: kim
 * @createTime: 2023-02-02  16:40
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, ResourceEntity> implements ResourceService {

    @Override
    public List<ResourceResp> listResources(Long parentId) {
        //查询接口资源数据
        List<ResourceEntity> resourceEntityList = baseMapper.selectList(new LambdaQueryWrapper<ResourceEntity>()
                .eq(Objects.nonNull(parentId), ResourceEntity::getParentId, parentId)
                .eq(ResourceEntity::getModular, Boolean.FALSE)
                .orderByAsc(ResourceEntity::getParentId));
        return BeanUtil.copyToList(resourceEntityList, ResourceResp.class);
    }

}
