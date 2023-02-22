package com.my9z.blog.config.permissions;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.my9z.blog.service.auth.UserAuthService;
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
public class UserPermissionsConfig implements StpInterface {

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
        String loginIdStr = StrUtil.str(loginId, StandardCharsets.UTF_8);
        long userId = NumberUtil.parseLong(loginIdStr);
        return userAuthService.userPermissionList(userId);
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
        return null;
    }
}