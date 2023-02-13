package com.my9z.blog.common.pojo.resp;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 菜单返回对象
 * @author: wczy9
 * @createTime: 2023-01-31  16:18
 */
@Data
public class MenuResp {

    /**
     * id
     */
    private Long id;

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
     * 排序
     */
    private Integer orderNum;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 子菜单列表
     */
    private List<MenuResp> children;

}
