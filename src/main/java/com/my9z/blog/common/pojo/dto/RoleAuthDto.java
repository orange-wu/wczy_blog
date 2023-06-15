package com.my9z.blog.common.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @description: 角色权限dto
 * @author: wczy9
 * @createTime: 2023-06-12  17:21
 */
@Data
public class RoleAuthDto {

    /**
     * 角色id
     */
    private Long id;

    /**
     * 接口权限集合
     */
    private List<String> permissionList;

    /**
     * 角色名
     */
    private String roleLabel;

}