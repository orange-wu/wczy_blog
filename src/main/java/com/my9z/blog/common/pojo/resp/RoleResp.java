package com.my9z.blog.common.pojo.resp;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 角色返回对象
 * @author: wczy9
 * @createTime: 2023-02-13  11:45
 */
@Data
public class RoleResp {

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色标签
     */
    private String roleLabel;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否禁用
     */
    private Boolean disable;

    /**
     * 资源id列表
     */
    private List<Long> resourceIds;

    /**
     * 菜单id列表
     */
    private List<Long> menuIds;

}