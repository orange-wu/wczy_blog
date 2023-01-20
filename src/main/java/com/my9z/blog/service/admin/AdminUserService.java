package com.my9z.blog.service.admin;

import com.my9z.blog.common.pojo.dto.LoginUserDTO;

/**
 * @description: admin后台用户相关操作service
 * @author: wczy9
 * @createTime: 2023-01-20  14:16
 */
public interface AdminUserService {

    /**
     * admin后台登陆
     *
     * @param loginUser 登陆信息dto
     */
    void login(LoginUserDTO loginUser);

}