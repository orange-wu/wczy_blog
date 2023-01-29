package com.my9z.blog.common.pojo.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @description: 登陆用户req
 * @author: wczy9
 * @createTime: 2023-01-20  12:51
 */
@Data
public class LoginUserReq {

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

}