package com.my9z.blog.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.my9z.blog.common.pojo.Result;
import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.req.LoginUserReq;
import com.my9z.blog.common.pojo.req.SearchUserReq;
import com.my9z.blog.common.pojo.resp.UserInfoResp;
import com.my9z.blog.common.pojo.resp.UserPageInfoResp;
import com.my9z.blog.service.admin.AdminUserService;
import com.my9z.blog.service.auth.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private UserAuthService userAuthService;

    @SaIgnore
    @PostMapping("/login")
    public Result<UserInfoResp> login(LoginUserReq loginUser) {
        return Result.ok(adminService.login(loginUser));
    }

    @GetMapping("/admin/users")
    @SaCheckPermission(value = "users-list", orRole = "admin")
    public Result<WPage<UserPageInfoResp>> listUsers(SearchUserReq searchUserReq) {
        return Result.ok(userAuthService.pageUsers(searchUserReq));
    }
}
