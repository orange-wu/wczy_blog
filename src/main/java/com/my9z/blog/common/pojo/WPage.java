package com.my9z.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @description: 分页请求参数
 * @author: wczy9
 * @createTime: 2023-02-13  11:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WPage<T> {

    /**
     * 当前页
     */
    private Long pageNumber = 1L;

    /**
     * 条数
     */
    private Long pageSize = 10L;

    /**
     * 总数
     */
    private Long total = 0L;

    /**
     * 数据列表
     */
    private List<T> records = Collections.emptyList();

}