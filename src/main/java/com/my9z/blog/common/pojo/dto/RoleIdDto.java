package com.my9z.blog.common.pojo.dto;

import lombok.Data;

/**
 * @description: 角色id实体类
 * @author: wczy9
 * @createTime: 2023-03-16  14:19
 */
@Data
public class RoleIdDto {

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色名
     */
    private String roleName;

}