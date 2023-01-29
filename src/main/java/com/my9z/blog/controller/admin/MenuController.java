package com.my9z.blog.controller.admin;

import com.my9z.blog.common.pojo.Result;
import com.my9z.blog.common.pojo.resq.UserMenuResp;
import com.my9z.blog.service.auth.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 菜单相关接口
 * @author: wczy9
 * @createTime: 2023-01-21  18:53
 */
@Slf4j
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;


    @GetMapping("/admin/user/menus")
    public Result<List<UserMenuResp>> listUserMenus() {
        return Result.ok(menuService.listUserMenus());
    }

}