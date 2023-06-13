package com.my9z.blog.service.auth;

import java.util.Collection;

/**
 * @description: 系统权限service
 * @author: wczy9
 * @createTime: 2023-06-13  22:23
 */
public interface SystemAuthService {

    /**
     * 刷新系统权限的所有缓存
     */
    void refreshAuthAllCache();

    /**
     * 删除指定角色id的用户权限缓存
     *
     * @param roleIdColl 角色id集合
     */
    void deleteRoleUserCache(Collection<Long> roleIdColl);

    /**
     * 删除指定角色id的接口权限缓存
     *
     * @param roleIdColl 角色id集合
     */
    void deleteRolePermissionCache(Collection<Long> roleIdColl);

}
