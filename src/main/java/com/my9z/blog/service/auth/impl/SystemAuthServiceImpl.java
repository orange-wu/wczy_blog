package com.my9z.blog.service.auth.impl;

import cn.hutool.core.collection.CollUtil;
import com.my9z.blog.common.constant.RedisKeyConstant;
import com.my9z.blog.common.enums.ErrorCodeEnum;
import com.my9z.blog.common.pojo.dto.RoleAuthDto;
import com.my9z.blog.common.pojo.dto.RoleUserDto;
import com.my9z.blog.service.auth.RoleService;
import com.my9z.blog.service.auth.SystemAuthService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description: 系统权限service实现类
 * @author: wczy9
 * @createTime: 2023-06-13  22:23
 */
@Service
public class SystemAuthServiceImpl implements SystemAuthService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void refreshAuthAllCache() {
        List<RoleAuthDto> roleAuthDtoList = roleService.roleAuthList();
        if (CollUtil.isEmpty(roleAuthDtoList)) {
            throw ErrorCodeEnum.NOT_HAVE_ANY_ROLE.buildException();
        }
        //刷新角色的接口资源缓存
        refreshRolePermissionAllCache(roleAuthDtoList);
        //刷新角色的用户信息缓存
        refreshRoleUserAllCache(roleAuthDtoList);
    }

    @Override
    public void deleteRoleUserCache(Collection<Long> roleIdColl) {
        if (CollUtil.isEmpty(roleIdColl)) return;
        String roleUserKey = RedisKeyConstant.getRoleUserKey();
        RMap<Long, RoleUserDto> roleUserCache = redissonClient.getMap(roleUserKey);
        roleUserCache.fastRemove(roleIdColl.toArray(new Long[0]));
    }

    @Override
    public void deleteRolePermissionCache(Collection<Long> roleIdColl) {
        if (CollUtil.isEmpty(roleIdColl)) return;
        String rolePermissionKey = RedisKeyConstant.getRoleAuthKey();
        RMap<Long, List<String>> rolePermissionCache = redissonClient.getMap(rolePermissionKey);
        rolePermissionCache.fastRemove(roleIdColl.toArray(new Long[0]));
    }

    /**
     * 刷新角色的接口资源缓存
     *
     * @param roleAuthDtoList 用户权限对象
     */
    private void refreshRolePermissionAllCache(List<RoleAuthDto> roleAuthDtoList) {
        Map<Long, List<String>> roleResourceAuth = new HashMap<>();
        for (RoleAuthDto roleAuthDto : roleAuthDtoList) {
            if (CollUtil.isEmpty(roleAuthDto.getPermissionList())) {
                roleResourceAuth.put(roleAuthDto.getId(), new ArrayList<>());
            } else {
                roleResourceAuth.put(roleAuthDto.getId(), roleAuthDto.getPermissionList());
            }
        }
        String rolePermissionKey = RedisKeyConstant.getRoleAuthKey();
        RMap<Long, List<String>> rolePermissionCache = redissonClient.getMap(rolePermissionKey);
        rolePermissionCache.putAll(roleResourceAuth);
    }

    /**
     * 刷新角色的用户缓存
     *
     * @param roleAuthDtoList 用户权限对象
     */
    private void refreshRoleUserAllCache(List<RoleAuthDto> roleAuthDtoList) {
        List<RoleUserDto> roleUserDtoList = new ArrayList<>(CollUtil.trans(roleAuthDtoList, (roleAuth) -> {
            RoleUserDto roleUserDto = new RoleUserDto();
            roleUserDto.setId(roleAuth.getId());
            roleUserDto.setRoleLabel(roleAuth.getRoleLabel());
            roleUserDto.setUseIds(roleAuth.getUseIds());
            return roleUserDto;
        }));
        Map<Long, RoleUserDto> roleUserMap = roleUserDtoList.stream()
                .collect(Collectors.toMap(RoleUserDto::getId, Function.identity()));
        String roleUserKey = RedisKeyConstant.getRoleUserKey();
        RMap<Long, RoleUserDto> roleUserCache = redissonClient.getMap(roleUserKey);
        roleUserCache.putAll(roleUserMap);
    }
}