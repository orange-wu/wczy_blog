package com.my9z.blog.common.pojo;

import lombok.Data;

/**
 * @description: 分页请求参数
 * @author: wczy9
 * @createTime: 2023-02-13  11:53
 */
@Data
public class Page {

    /**
     * 当前页
     */
    private Integer pageNumber;

    /**
     * 条数
     */
    private Integer pageSize;

}