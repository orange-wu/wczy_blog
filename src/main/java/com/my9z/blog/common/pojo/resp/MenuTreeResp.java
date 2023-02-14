package com.my9z.blog.common.pojo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 菜单树形返回对象
 * @author: wczy9
 * @createTime: 2023-02-13  16:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuTreeResp {

    /**
     * 菜单id
     */
    private Long id;

    /**
     * 菜单名
     */
    private String label;

    /**
     * 子选项
     */
    private List<MenuTreeResp> children;

}