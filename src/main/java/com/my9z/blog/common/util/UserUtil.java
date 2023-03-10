package com.my9z.blog.common.util;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;

import java.util.Collection;

/**
 * @description: 用户工具类
 * @author: wczy9
 * @createTime: 2023-01-21  17:29
 */
public class UserUtil {

    /**
     * 登陆
     *
     * @param id 用户id
     */
    public static void login(Long id) {
        StpUtil.login(id);
    }

    /**
     * 判断当前会话是否已经登陆
     *
     * @return 是否登陆
     */
    public static boolean isLogin() {
        return StpUtil.isLogin();
    }

    /**
     * 判断当前会话是否已经登陆,没登陆则抛出异常
     */
    public static void checkLogin() {
        StpUtil.checkLogin();
    }

    /**
     * 获取当前登陆用户的id
     *
     * @return 当前登陆用户的id
     */
    public static Long getLoginId() {
        return StpUtil.getLoginIdAsLong();
    }

    /**
     * 通过用户id强制登出用户
     *
     * @param ids 当前登陆的用户id集合
     */
    public static void logout(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) return;
        ids.forEach(id -> {
            if (StpUtil.getTokenValueByLoginId(id) != null) StpUtil.logout(id);
        });
    }

}
