package com.my9z.blog;

import com.alibaba.fastjson.JSON;
import com.my9z.blog.common.pojo.req.LoginUserReq;
import com.my9z.blog.common.pojo.resp.MenuResp;
import com.my9z.blog.common.pojo.resp.ResourceResp;
import com.my9z.blog.common.pojo.resp.UserInfoResp;
import com.my9z.blog.common.pojo.resp.UserMenuResp;
import com.my9z.blog.service.admin.AdminUserService;
import com.my9z.blog.service.auth.MenuService;
import com.my9z.blog.service.auth.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @description: 测试类
 * @author: wczy9
 * @createTime: 2023-01-28  10:45
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SpringTest {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ResourceService resourceService;

    @Test
    public void userMenuTest() {
        LoginUserReq loginUserReq = new LoginUserReq();
        loginUserReq.setUsername("admin@qq.com");
        loginUserReq.setPassword("123456");
        UserInfoResp login = adminUserService.login(loginUserReq);
        log.info("login userinfo:{}", JSON.toJSONString(login));
        List<UserMenuResp> userMenuResp = menuService.listUserMenus();
        log.info("login menuList:{}", JSON.toJSONString(userMenuResp));
    }

    @Test
    public void userTest() {
        List<MenuResp> menuResp = menuService.listMenus("");
        log.info("menuResp:{}", JSON.toJSONString(menuResp));
    }

    @Test
    public void deleteMenuTest() {
        menuService.deleteMenuById(219L);
    }

    @Test
    public void resourceListTest() {
        List<ResourceResp> resourceResp = resourceService.listResources(null);
        log.info("resourceResp:{}",JSON.toJSONString(resourceResp));
    }

}
