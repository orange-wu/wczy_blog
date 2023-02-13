package com.my9z.blog.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my9z.blog.common.pojo.entity.auth.MenuEntity;
import com.my9z.blog.common.pojo.req.SaveOrUpdateMenuReq;
import com.my9z.blog.common.pojo.resp.MenuResp;
import com.my9z.blog.common.pojo.resp.UserMenuResp;

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

    /**
     * 通过菜单id删除对应菜单，如果有子菜单则一并删除
     * 如果有对应的角色拥有该菜单的权限，则会报错提示
     *
     * @param menuId 菜单id
     */
    void deleteMenuById(Long menuId);

    /**
     * 新增或修改菜单
     *
     * @param saveOrUpdateMenuReq 菜单新增/修改请求对西欧
     */
    void saveOrUpdateMenu(SaveOrUpdateMenuReq saveOrUpdateMenuReq);
}
