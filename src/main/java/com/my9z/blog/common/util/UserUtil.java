package com.my9z.blog.common.util;

import cn.dev33.satoken.stp.StpUtil;

/**
 * @description: 用户工具类
 * @author: wczy9
 * @createTime: 2023-01-21  17:29
 */
public class UserUtil {

    /**
     * 获取当前登陆用户的id
     *
     * @return 当前登陆用户的id
     */
    public static Long getLoginId() {
        return (Long) StpUtil.getLoginId();
    }

}