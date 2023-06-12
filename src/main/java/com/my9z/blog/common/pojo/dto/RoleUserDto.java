package com.my9z.blog.common.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @description: 角色用户dto
 * @author: wczy9
 * @createTime: 2023-06-12  19:59
 */
@Data
public class RoleUserDto {

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色名
     */
    private String roleLabel;

    /**
     * 用户id集合
     */
    private List<Long> useIds;

}