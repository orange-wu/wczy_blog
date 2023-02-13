package com.my9z.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my9z.blog.common.pojo.Result;
import com.my9z.blog.common.pojo.req.SearchRoleReq;
import com.my9z.blog.common.pojo.resp.RoleResp;
import com.my9z.blog.service.auth.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 角色相关接口
 * @author: wczy9
 * @createTime: 2023-02-13  11:38
 */
@Slf4j
@RestController()
@RequestMapping("/admin")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 分页查询用户列表
     *
     * @param searchRoleReq 分页查询入参
     * @return 用户信息分页数据
     */
    @GetMapping("/roles")
    public Result<Page<RoleResp>> listRoles(SearchRoleReq searchRoleReq) {
        return Result.ok(roleService.listRoles(searchRoleReq));
    }

}
