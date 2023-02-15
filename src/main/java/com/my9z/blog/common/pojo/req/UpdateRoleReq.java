package com.my9z.blog.common.pojo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 修改角色请求对象
 * @author: wczy9
 * @createTime: 2023-02-15  10:08
 */
@Data
public class UpdateRoleReq {

    /**
     * id
     */
    @NotNull(message = "异常数据：id为空")
    private Long id;

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