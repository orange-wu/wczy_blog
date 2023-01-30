package com.my9z.blog.common.enums;

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
    ;

    private final Integer code;

    private final String msg;

    public BusinessException buildException() {
        return new BusinessException(this.code, this.msg);
    }

}
