package com.my9z.blog.common.constant;

import cn.hutool.core.util.StrUtil;

/**
 * @description: redis key常量
 * @author: wczy9
 * @createTime: 2023-03-01  09:17
 */
public interface RedisKeyConstant {

    String WCZY_BLOG = "wczy_blog";

    String AUTH = "authentication";

    String USER = "user";

    String PERMISSION = "permission";

    String ROLE = "role";

    /**
     * 获取用户权限码缓存的keu
     *
     * @return 用户权限码的redis key
     */
    static String getUserPermissionKey() {
        return StrUtil.join(StrUtil.COLON, WCZY_BLOG, AUTH, USER, PERMISSION);
    }

    /**
     * 获取用户角色缓存的keu
     *
     * @return 用户角色缓存的redis key
     */
    static String getUserRoleKey() {
        return StrUtil.join(StrUtil.COLON, WCZY_BLOG, AUTH, USER, ROLE);
    }

    /**
     * 获取角色权限缓存的keu
     *
     * @return 角色权限缓存的redis key
     */
    static String getRoleAuthKey() {
        return StrUtil.join(StrUtil.COLON, WCZY_BLOG, AUTH, ROLE, PERMISSION);
    }

    /**
     * 获取角色角色缓存的keu
     *
     * @return 角色角色缓存的redis key
     */
    static String getRoleUserKey() {
        return StrUtil.join(StrUtil.COLON, WCZY_BLOG, AUTH, ROLE, USER);
    }
}