package com.my9z.blog.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my9z.blog.common.pojo.entity.auth.MenuEntity;
import com.my9z.blog.common.pojo.resq.MenuResp;
import com.my9z.blog.common.pojo.resq.UserMenuResp;

import java.util.List;

/**
 * @description: 菜单service
 * @author: wczy9
 * @createTime: 2023-01-21  17:36
 */
public interface MenuService extends IService<MenuEntity> {

    /**
     * 查看用户菜单
     *
     * @return 菜单列表
     */
    List<UserMenuResp> listUserMenus();

    /**
     * 根据菜单名查询菜单列表
     *
     * @param menuName 菜单名
     * @return 菜单列表
     */
    List<MenuResp> listMenus(String menuName);
}
