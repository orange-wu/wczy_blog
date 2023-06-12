package com.my9z.blog.common.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @description: 用户权限dto
 * @author: wczy9
 * @createTime: 2023-06-12  17:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleAuthDto extends RoleUserDto {

    /**
     * 资源id集合
     */
    private List<String> permissionList;

}