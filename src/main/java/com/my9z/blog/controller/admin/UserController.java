package com.my9z.blog.controller.admin;

import com.my9z.blog.common.pojo.Result;
import com.my9z.blog.common.pojo.dto.LoginUserDTO;
import com.my9z.blog.service.admin.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 用户相关接口
 * @author: wczy9
 * @createTime: 2023-01-20  12:09
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private AdminUserService adminService;

    @PostMapping("/login")
    public Result<?> login(LoginUserDTO loginUser) {
        adminService.login(loginUser);
        return Result.ok();
    }

}