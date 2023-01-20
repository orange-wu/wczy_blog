package com.my9z.blog.common.entity.dto;

import lombok.Data;

/**
 * @description: 登陆用户dto
 * @author: wczy9
 * @createTime: 2023-01-20  12:51
 */
@Data
public class LoginUserDTO {

    /**
     * 用户吗
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}