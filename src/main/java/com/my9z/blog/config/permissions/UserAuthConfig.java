package com.my9z.blog.config.permissions;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.my9z.blog.common.constant.RedisKeyConstant;
import com.my9z.blog.service.auth.UserAuthService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @description: 用户权限配置类
 * @author: wczy9
 * @createTime: 2023-02-20  17:32
 */
@Component
public class UserAuthConfig implements StpInterface {

    // TODO: 2023/2/28 每次接口鉴权都要去查 改为redis
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private UserAuthService userAuthService;

    /**
     * 返回指定账号id所拥有的权限码集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String userPermissionKey = RedisKeyConstant.getUserPermissionKey();
        RMap<Long, List<String>> userPermissionCache = redissonClient.getMap(userPermissionKey);
        long userId = getUserId(loginId);
        //从缓存中查寻该用户权限码
        if (CollUtil.isNotEmpty(userPermissionCache) && userPermissionCache.containsKey(userId)) {
            return userPermissionCache.get(userId);
        }
        //缓存中没有从表中查询
        List<String> permissionList = userAuthService.userPermissionList(userId);
        //存入缓存
        userPermissionCache.fastPut(userId, permissionList);
        return permissionList;
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
        String userRoleKey = RedisKeyConstant.getUserRoleKey();
        RMap<Long, List<String>> userRoleCache = redissonClient.getMap(userRoleKey);
        long userId = getUserId(loginId);
        //从缓存中查寻该用户角色
        if (CollUtil.isNotEmpty(userRoleCache) && userRoleCache.containsKey(userId)) {
            return userRoleCache.get(userId);
        }
        //缓存中没有从表中查询
        List<String> roleList = userAuthService.userRoleList(userId);
        //存入缓存
        userRoleCache.fastPut(userId, roleList);
        return roleList;
    }

    /**
     * userId转换long类型
     *
     * @param loginId object类型的id
     * @return long类型的id
     */
    private static long getUserId(Object loginId) {
        String loginIdStr = StrUtil.str(loginId, StandardCharsets.UTF_8);
        return NumberUtil.parseLong(loginIdStr);
    }
}