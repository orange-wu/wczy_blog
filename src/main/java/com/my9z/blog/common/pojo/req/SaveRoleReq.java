package com.my9z.blog.common.pojo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @description: 新增角色请求对象
 * @author: wczy9
 * @createTime: 2023-02-14  16:20
 */
@Data
public class SaveRoleReq {

    /**
     * 角色名
     */
    @NotBlank(message = "角色名不能为空")
    private String roleName;

    /**
     * 权限名
     */
    @NotBlank(message = "权限名不能为空")
    private String roleLabel;

    /**
     * 资源列表
     */
    private List<Long> resourceIds;

    /**
     * 菜单列表
     */
    private List<Long> menuIds;

}