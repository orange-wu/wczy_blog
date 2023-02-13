package com.my9z.blog.common.pojo.req;

import com.my9z.blog.common.pojo.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 角色查询请求对象
 * @author: wczy9
 * @createTime: 2023-02-13  13:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchRoleReq extends Page {

    /**
     * 角色名
     */
    private String roleName;

}