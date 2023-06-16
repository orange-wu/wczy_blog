package com.my9z.blog.service.auth;

import org.redisson.api.RList;

import java.util.Collection;
import java.util.List;

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
     * 删除指定用户id的角色权限缓存
     *
     * @param userIdColl 用户id集合
     */
    void deleteUserRoleCache(Collection<Long> userIdColl);

    /**
     * 删除指定角色标识的接口权限缓存
     *
     * @param roleLabelColl 角色标识集合
     */
    void deleteRolePermissionCache(Collection<String> roleLabelColl);

    /**
     * 从缓存查询用户的角色类型
     *
     * @param userId 用户id
     * @return 角色类型
     */
    RList<String> selectUserRoleLabelFromCache(Long userId);


    /**
     * 从数据库查询用户的角色类型，并存储redis
     *
     * @param userId 用户id
     * @return 角色类型
     */
    List<String> selectUserRoleLabelAndSaveCache(Long userId);

    /**
     * 从缓存查询角色的接口权限，未命中缓存的角色查询数据库，并且存储redis
     *
     * @param roleLabelList 角色标识
     * @return 接口权限
     */
    List<String> selectUserPermissionFromCacheAndSaveCache(List<String> roleLabelList);

}
