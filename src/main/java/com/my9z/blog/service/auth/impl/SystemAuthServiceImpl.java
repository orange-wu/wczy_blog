package com.my9z.blog.service.auth.impl;

import cn.hutool.core.collection.CollUtil;
import com.my9z.blog.common.constant.RedisKeyConstant;
import com.my9z.blog.common.enums.ErrorCodeEnum;
import com.my9z.blog.common.pojo.dto.RoleAuthDto;
import com.my9z.blog.service.auth.RoleService;
import com.my9z.blog.service.auth.SystemAuthService;
import com.my9z.blog.service.auth.UserAuthService;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
    private UserAuthService userAuthService;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void refreshAuthAllCache() {
        List<RoleAuthDto> roleAuthDtoList = roleService.roleAuthList(null);
        if (CollUtil.isEmpty(roleAuthDtoList)) {
            throw ErrorCodeEnum.NOT_HAVE_ANY_ROLE.buildException();
        }
        //刷新角色的接口资源缓存
        refreshRolePermissionCache(roleAuthDtoList);
    }

    @Override
    public void deleteRoleUserCache(Collection<Long> roleIdColl) {

    }

    @Override
    public void deleteRolePermissionCache(Collection<Long> roleIdColl) {
        if (CollUtil.isEmpty(roleIdColl)) return;
        String rolePermissionKey = RedisKeyConstant.getRoleAuthKey();
        RMap<Long, List<String>> rolePermissionCache = redissonClient.getMap(rolePermissionKey);
        rolePermissionCache.fastRemove(roleIdColl.toArray(new Long[0]));
    }

    @Override
    public List<String> selectUserRoleLabelFromCache(Long userId) {
        if (userId == null) return null;
        String userRoleKey = RedisKeyConstant.getUserRoleKey(userId);
        return redissonClient.getList(userRoleKey);
    }

    @Override
    public List<String> selectUserRoleLabelAndSaveCache(Long userId) {
        if (userId == null) return null;
        //查询数据库对应用户的角色权限
        List<String> userRoleList = userAuthService.selectRoleLabelByUserId(userId);
        //没有角色的用户也需要缓存
        if (CollUtil.isEmpty(userRoleList)) userRoleList = new ArrayList<>();
        //将角色权限存储缓存（用户的角色权限缓存统一一个小时过期时间）
        String userRoleKey = RedisKeyConstant.getUserRoleKey(userId);
        RList<String> userRoleCache = redissonClient.getList(userRoleKey);
        userRoleCache.addAll(userRoleList);
        // TODO: 2023/6/15 常量值替换
        userRoleCache.expire(1, TimeUnit.HOURS);
        //返回数据
        return userRoleList;
    }

    @Override
    public List<String> selectUserPermissionFromCacheAndSaveCache(List<String> roleLabelList) {
        if (CollUtil.isEmpty(roleLabelList)) return null;
        //查询角色接口权限的map缓存
        String rolePermissionKey = RedisKeyConstant.getRoleAuthKey();
        RMap<String, List<String>> rolePermissionCache = redissonClient.getMap(rolePermissionKey);
        if (CollUtil.isEmpty(rolePermissionCache)) {
            throw ErrorCodeEnum.ROLE_CACHE_IS_ERROR.buildException();
        }
        //统计缓存返回的接口权限
        HashSet<String> permissionSet = CollUtil.newHashSet();
        roleLabelList.forEach(roleLabel -> {
            if (rolePermissionCache.containsKey(roleLabel)) {
                permissionSet.addAll(rolePermissionCache.get(roleLabel));
            }
        });
        //所有角色都命中缓存直接返回缓存中的接口权限，存在未命中的情况的话，查询数据库然后两边合集
        if (!rolePermissionCache.keySet().containsAll(roleLabelList)) {
            //存在有角色缓存未命中的情况，查询数据库
            List<String> refreshRoleList = (List<String>) CollUtil.disjunction(rolePermissionCache.keySet(), roleLabelList);
            List<String> newUserpermissionList = selectUserPermissionAndSaveCache(refreshRoleList);
            //与缓存中的接口权限做合集
            if (newUserpermissionList != null) {
                permissionSet.addAll(newUserpermissionList);
            }
        }
        return new ArrayList<>(permissionSet);
    }

    private List<String> selectUserPermissionAndSaveCache(List<String> roleLabelList) {
        if (CollUtil.isEmpty(roleLabelList)) return null;
        //从数据库查询角色的接口权限
        List<RoleAuthDto> roleAuthList = roleService.roleAuthList(roleLabelList);
        //刷新对应角色的缓存
        refreshRolePermissionCache(roleAuthList);
        //返回数据
        return roleAuthList.stream()
                .map(RoleAuthDto::getPermissionList)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * 刷新角色的接口资源缓存
     *
     * @param roleAuthDtoList 用户权限对象
     */
    private void refreshRolePermissionCache(List<RoleAuthDto> roleAuthDtoList) {
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

}