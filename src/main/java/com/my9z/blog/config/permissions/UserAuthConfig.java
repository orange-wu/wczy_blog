package com.my9z.blog.config.permissions;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.my9z.blog.service.auth.SystemAuthService;
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

    @Autowired
    private SystemAuthService systemAuthService;


    /**
     * 返回指定账号id所拥有的权限码集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        //查询用户的角色
        List<String> roleList = getRoleList(loginId, loginType);
        if (CollUtil.isEmpty(roleList)) return null;
        //从缓存中查寻角色的接口权限，并且未命中缓存的角色查询数据库然后刷新缓存
        return systemAuthService.selectUserPermissionFromCacheAndSaveCache(roleList);
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
        Long userId = getUserId(loginId);
        //从缓存中查寻该用户角色
        List<String> userRoleCache = systemAuthService.selectUserRoleLabelFromCache(userId);
        //userRoleCache不为空就代表命中缓存，就算userRoleCache的size为空
        if (userRoleCache != null) return userRoleCache;
        //缓存中没有，数据库中查询,并存储redis
        return systemAuthService.selectUserRoleLabelAndSaveCache(userId);
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

}