package com.my9z.blog.common.pojo.req;

import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.resp.ResourceResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 接口资源查询请求对象
 * @author: wczy9
 * @createTime: 2023-02-18  14:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchResourceReq extends WPage<ResourceResp> {

    /**
     * 模块名id集合 逗号分割
     */
    private String parentId;

}