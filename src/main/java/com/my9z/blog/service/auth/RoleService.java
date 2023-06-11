package com.my9z.blog.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.dto.RoleIdDto;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
import com.my9z.blog.common.pojo.req.SaveRoleReq;
import com.my9z.blog.common.pojo.req.SearchRoleReq;
import com.my9z.blog.common.pojo.req.UpdateRoleReq;
import com.my9z.blog.common.pojo.resp.RoleResp;

import java.util.List;

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
    WPage<RoleResp> listRoles(SearchRoleReq searchRoleReq);

    /**
     * 新增角色
     *
     * @param saveRoleReq 新增请求对象
     */
    void saveRole(SaveRoleReq saveRoleReq);

    /**
     * 修改角色
     *
     * @param updateRoleReq 修改角色对象
     */
    void updateRole(UpdateRoleReq updateRoleReq);

    /**
     * 删除角色
     *
     * @param roleId 角色id
     */
    void deleteRoleById(Long roleId);

    /**
     * 查询所有启用状态下的角色名 角色id
     *
     * @return {@link List<RoleIdDto>} 角色id实体类
     */
    List<RoleIdDto> listEnableRoleNameAndId();
}