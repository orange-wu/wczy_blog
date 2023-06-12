package com.my9z.blog.config.permissions;

import cn.hutool.core.collection.CollUtil;
import com.my9z.blog.common.constant.RedisKeyConstant;
import com.my9z.blog.common.enums.ErrorCodeEnum;
import com.my9z.blog.common.pojo.dto.RoleAuthDto;
import com.my9z.blog.common.pojo.dto.RoleUserDto;
import com.my9z.blog.service.auth.RoleService;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 角色权限缓存刷新
 * @author: wczy9
 * @createTime: 2023-06-12  17:02
 */
@Component
public class RoleAuthCacheRefreshConfig implements InitializingBean {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void afterPropertiesSet() {
        List<RoleAuthDto> roleAuthDtoList = roleService.roleAuthList();
        if (CollUtil.isEmpty(roleAuthDtoList)) {
            throw ErrorCodeEnum.NOT_HAVE_ANY_ROLE.buildException();
        }
        //存储角色的接口资源缓存
        refreshRoleAuthKey(roleAuthDtoList);
        //存储角色的用户信息缓存
        refreshRoleUserKey(roleAuthDtoList);
    }

    /**
     * 刷新角色的接口资源缓存
     *
     * @param roleAuthDtoList 用户权限对象
     */
    public void refreshRoleAuthKey(List<RoleAuthDto> roleAuthDtoList) {
        HashMap<Long, List<String>> roleResourceAuth = new HashMap<>();
        for (RoleAuthDto roleAuthDto : roleAuthDtoList) {
            if (CollUtil.isEmpty(roleAuthDto.getPermissionList())) {
                roleResourceAuth.put(roleAuthDto.getId(), new ArrayList<>());
            } else {
                roleResourceAuth.put(roleAuthDto.getId(), roleAuthDto.getPermissionList());
            }
        }
        String roleAuthKey = RedisKeyConstant.getRoleAuthKey();
        RMap<Long, List<String>> roleResourceCache = redissonClient.getMap(roleAuthKey);
        roleResourceCache.putAll(roleResourceAuth);
    }

    /**
     * 刷新角色的用户缓存
     *
     * @param roleAuthDtoList 用户权限对象
     */
    public void refreshRoleUserKey(List<RoleAuthDto> roleAuthDtoList) {
        List<RoleUserDto> roleUserDtoList = new ArrayList<>(CollUtil.trans(roleAuthDtoList, (roleAuth) -> {
            RoleUserDto roleUserDto = new RoleUserDto();
            roleUserDto.setId(roleAuth.getId());
            roleUserDto.setRoleLabel(roleAuth.getRoleLabel());
            roleUserDto.setUseIds(roleAuth.getUseIds());
            return roleUserDto;
        }));
        String roleUserKey = RedisKeyConstant.getRoleUserKey();
        RBucket<List<RoleUserDto>> roleUserIdKey = redissonClient.getBucket(roleUserKey);
        roleUserIdKey.set(roleUserDtoList);
    }
}