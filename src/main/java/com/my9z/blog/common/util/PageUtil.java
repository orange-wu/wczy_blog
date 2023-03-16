package com.my9z.blog.common.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my9z.blog.common.pojo.WPage;

/**
 * @description: 分页工具类
 * @author: wczy9
 * @createTime: 2023-03-16  15:00
 */
public class PageUtil {

    /**
     * 将包米豆的分页对象转换成WPage
     *
     * @param page 包米豆分页对象
     * @param <T>  数据范性
     * @return WPage分页对象
     */
    public static <T> WPage<T> convert(Page<T> page) {
        return new WPage<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

}