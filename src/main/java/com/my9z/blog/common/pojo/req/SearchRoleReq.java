package com.my9z.blog.common.pojo.req;

import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.resp.RoleResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 角色查询请求对象
 * @author: wczy9
 * @createTime: 2023-02-13  13:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchRoleReq extends WPage<RoleResp> {

    /**
     * 角色名
     */
    private String roleName;

}