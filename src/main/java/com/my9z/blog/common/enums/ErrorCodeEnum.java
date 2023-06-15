package com.my9z.blog.common.enums;

import cn.hutool.core.util.StrUtil;
import com.my9z.blog.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 错误码枚举
 * @author: wczy
 * @createTime: 2023-01-w0  15.01
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    /***** 权限相关 *****/
    LOGIN_USER_PASSWORD_ERROR(1000, "用户名或密码错误"),
    LOGIN_SA_TOKEN_ERROR(1001, "登陆失败"),
    USER_IS_DISABLE(1002, "账号已经被禁用"),
    USER_INFO_IS_ABNORMAL(1003, "当前用户信息异常"),
    ROLE_CACHE_IS_ERROR(1004, "角色的接口权限缓存异常"),

    /***** 菜单相关 *****/
    MENU_IS_USED_BY_ROLE(2000, "当前菜单被用户:{}关联"),

    /***** 接口资源相关 *****/
    RESOURCE_IS_USED_BY_ROLE(3000, "当前接口资源被用户:{}关联"),

    /***** 角色相关 *****/
    ROLE_NAME_OR_LABEL_ALREADY_EXIST(4000, "此角色名或权限名已经存在"),
    ROLE_DATA_IS_NOT_EXIST(4001, "当前角色数据不存在"),
    ROLE_USER_EXIST(4002, "当前角色下存在实际用户"),
    ROLE_NAME_ALREADY_EXIST(4003, "此角色名已经存在"),
    NOT_HAVE_ANY_ROLE(4004, "没有任何一个角色存在"),

    /***** 用户相关 *****/
    USER_DATA_IS_NOT_EXIST(5000, "当前用户数据不存在"),
    ;
    private final Integer code;

    private final String msg;

    /**
     * 构建业务异常
     *
     * @param info 补充信息 配合枚举中msg使用，需要使用到info时，msg要有对应数目的"{}"
     * @return BusinessException 业务异常
     */
    public BusinessException buildException(Object... info) {
        return new BusinessException(this.code, StrUtil.format(this.msg, info));
    }

}
