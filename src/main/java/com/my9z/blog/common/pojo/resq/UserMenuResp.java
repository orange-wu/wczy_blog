package com.my9z.blog.common.pojo.resq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 用户菜单返回对象
 * @author: wczy9
 * @createTime: 2023-01-21  19:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMenuResp {

    /**
     * 菜单名
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * icon
     */
    private String icon;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 子菜单列表
     */
    private List<UserMenuResp> children;

}
