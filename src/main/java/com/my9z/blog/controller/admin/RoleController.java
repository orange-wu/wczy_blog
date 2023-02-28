package com.my9z.blog.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.my9z.blog.common.pojo.Result;
import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.req.SaveRoleReq;
import com.my9z.blog.common.pojo.req.SearchRoleReq;
import com.my9z.blog.common.pojo.req.UpdateRoleReq;
import com.my9z.blog.common.pojo.resp.RoleResp;
import com.my9z.blog.service.auth.RoleService;
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
    @SaCheckPermission(value = "roles-list", orRole = "admin")
    public Result<WPage<RoleResp>> listRoles(SearchRoleReq searchRoleReq) {
        return Result.ok(roleService.listRoles(searchRoleReq));
    }

    /**
     * 新增用户
     *
     * @param saveRoleReq 新增用户请求对象
     * @return {@link Result<>}
     */
    @PostMapping("/saveRole")
    @SaCheckPermission(value = "roles-save", orRole = "admin")
    public Result<?> saveRole(@RequestBody @Valid SaveRoleReq saveRoleReq) {
        roleService.saveRole(saveRoleReq);
        return Result.ok();
    }

    /**
     * 修改用户
     *
     * @param updateRoleReq 修改用户请求对象
     * @return {@link Result<>}
     */
    @PostMapping("/updateRole")
    @SaCheckPermission(value = "roles-update", orRole = "admin")
    public Result<?> updateRole(@RequestBody @Valid UpdateRoleReq updateRoleReq) {
        roleService.updateRole(updateRoleReq);
        return Result.ok();
    }

    /**
     * 删除角色
     *
     * @param roleId 角色id
     * @return {@link Result<>}
     */
    @DeleteMapping("/roles/{roleId}")
    @SaCheckPermission(value = "roles-delete", orRole = "admin")
    public Result<?> deleteRoleById(@PathVariable("roleId") Long roleId) {
        roleService.deleteRoleById(roleId);
        return Result.ok();
    }

}
