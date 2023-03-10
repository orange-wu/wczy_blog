package com.my9z.blog.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.my9z.blog.common.pojo.Result;
import com.my9z.blog.common.pojo.req.SaveOrUpdateMenuReq;
import com.my9z.blog.common.pojo.resp.MenuResp;
import com.my9z.blog.common.pojo.resp.MenuTreeResp;
import com.my9z.blog.common.pojo.resp.UserMenuResp;
import com.my9z.blog.service.auth.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @description: 菜单相关接口
 * @author: wczy9
 * @createTime: 2023-01-21  18:53
 */
@Slf4j
@RestController()
@RequestMapping("/admin")
public class MenuController {

    @Autowired
    private MenuService menuService;


    /**
     * 获取登录用户的菜单列表，用户前端渲染路由
     *
     * @return 当前登录用户的菜单列表
     */
    @GetMapping("/user/menus")
    @SaCheckPermission(value = "menus-user-list", orRole = "admin")
    public Result<List<UserMenuResp>> listUserMenus() {
        return Result.ok(menuService.listUserMenus());
    }

    /**
     * 获取所有菜单列表
     *
     * @return 所有菜单列表
     */
    @GetMapping("/menus")
    @SaCheckPermission(value = "menus-list", orRole = "admin")
    public Result<List<MenuResp>> listMenus(String menuName) {
        return Result.ok(menuService.listMenus(menuName));
    }


    /**
     * 根据菜单id删除id 物理删除
     *
     * @param menuId 菜单id
     * @return {@link Result<>}
     */
    @DeleteMapping("/menus/{menuId}")
    @SaCheckPermission(value = "menus-delete", orRole = "admin")
    public Result<?> deleteMenu(@PathVariable("menuId") Long menuId) {
        menuService.deleteMenuById(menuId);
        return Result.ok();
    }

    /**
     * 新增或修改菜单 id为空认为是新增数据
     *
     * @return {@link Result<>}
     */
    @PostMapping("/menus")
    @SaCheckPermission(value = "menus-saveOrUpdate", orRole = "admin")
    public Result<?> saveOrUpdateMenu(@Valid @RequestBody SaveOrUpdateMenuReq saveOrUpdateMenuReq) {
        menuService.saveOrUpdateMenu(saveOrUpdateMenuReq);
        return Result.ok();
    }

    /**
     * 角色管理界面接口菜单数据接口
     *
     * @return 菜单数据树形结构
     */
    @GetMapping("/role/menus")
    @SaCheckPermission(value = "menus-role-list", orRole = "admin")
    public Result<List<MenuTreeResp>> listMenuTree() {
        return Result.ok(menuService.listMenuTree());
    }

}
