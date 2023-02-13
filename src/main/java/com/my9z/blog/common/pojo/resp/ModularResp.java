package com.my9z.blog.common.pojo.resp;

import lombok.Data;

/**
 * @description: 菜单模块返回对象
 * @author: wczy9
 * @createTime: 2023-02-04  13:50
 */
@Data
public class ModularResp {

    /**
     * 模块id
     */
    private Long modularId;

    /**
     * 模块名
     */
    private String modularValue;

}