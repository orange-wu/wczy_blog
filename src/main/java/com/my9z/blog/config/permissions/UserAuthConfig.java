package com.my9z.blog.config.permissions;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.my9z.blog.common.constant.RedisKeyConstant;
import com.my9z.blog.common.pojo.dto.RoleUserDto;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 用户权限配置类
 * @author: wczy9
 * @createTime: 2023-02-20  17:32
 */
@Component
public class UserAuthConfig implements StpInterface {


    @Autowired
    private RedissonClient redissonClient;


    /**
     * 返回指定账号id所拥有的权限码集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        //从缓存中查寻该用户权限码
        return getUserPermissionList(getUserId(loginId));
    }

    /**
     * 返回指定账号id所拥有的角色标识集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        //从缓存中查寻该用户角色
        return getUserRoleList(getUserId(loginId));
    }

    /**
     * userId转换long类型
     *
     * @param loginId object类型的id
     * @return long类型的id
     */
    private Long getUserId(Object loginId) {
        String loginIdStr = StrUtil.str(loginId, StandardCharsets.UTF_8);
        return NumberUtil.parseLong(loginIdStr);
    }

    /**
     * 获取当前用户的角色集合
     *
     * @param userId 用户id
     * @return 角色集合
     */
    private List<String> getUserRoleList(Long userId) {
        String roleUserKey = RedisKeyConstant.getRoleUserKey();
        RBucket<List<RoleUserDto>> roleUserCacheList = redissonClient.getBucket(roleUserKey);
        return roleUserCacheList.get().stream()
                .filter(roleUser -> CollUtil.contains(roleUser.getUseIds(), userId))
                .map(RoleUserDto::getRoleLabel)
                .collect(Collectors.toList());
    }

    private List<String> getUserPermissionList(Long userId) {
        //查询用户的角色id
        RBucket<List<RoleUserDto>> roleUserCacheList = redissonClient.getBucket(RedisKeyConstant.getRoleUserKey());
        List<Long> userRoleId = roleUserCacheList.get().stream()
                .filter(roleUser -> CollUtil.contains(roleUser.getUseIds(), userId))
                .map(RoleUserDto::getId)
                .collect(Collectors.toList());
        //取角色拥有资源权限的交集
        RMap<Long, List<String>> rolePermissionCache = redissonClient.getMap(RedisKeyConstant.getRoleAuthKey());
        Set<String> permissionSet = new HashSet<>();
        for (Long roleId : userRoleId) {
            if (!rolePermissionCache.containsKey(roleId)) continue;
            permissionSet.addAll(rolePermissionCache.get(roleId));
        }
        return new ArrayList<>(permissionSet);
    }


}