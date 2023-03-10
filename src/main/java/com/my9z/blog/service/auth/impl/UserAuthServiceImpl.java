package com.my9z.blog.service.auth.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my9z.blog.common.pojo.entity.auth.ResourceEntity;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
import com.my9z.blog.mapper.ResourceMapper;
import com.my9z.blog.mapper.RoleMapper;
import com.my9z.blog.mapper.UserAuthMapper;
import com.my9z.blog.service.auth.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 用户账号service实现类
 * @author: wczy9
 * @createTime: 2023-01-20  14:03
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuthEntity> implements UserAuthService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> userPermissionList(Long userId) {
        if (userId == null) {
            return null;
        }
        List<ResourceEntity> userResourceList = resourceMapper.userPermissionList(userId);
        if (CollUtil.isEmpty(userResourceList)) {
            return null;
        }
        return userResourceList.stream()
                .map(ResourceEntity::getPermission)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> userRoleList(Long userId) {
        if (userId == null) {
            return null;
        }
        List<RoleEntity> roleEntitieList = roleMapper.userRoleList(userId);
        if (CollUtil.isEmpty(roleEntitieList)) {
            return null;
        }
        return roleEntitieList.stream()
                .map(RoleEntity::getRoleLabel)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());
    }
}