package com.my9z.blog.service.auth;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
import com.my9z.blog.common.pojo.req.SearchRoleReq;
import com.my9z.blog.common.pojo.resp.RoleResp;

/**
 * @description: 角色service
 * @author: wczy9
 * @createTime: 2023-01-29  21:25
 */
public interface RoleService extends IService<RoleEntity> {

    /**
     * 分页查询用户列表
     *
     * @param searchRoleReq 分页查询入参
     * @return 用户信息分页数据
     */
    Page<RoleResp> listRoles(SearchRoleReq searchRoleReq);
}