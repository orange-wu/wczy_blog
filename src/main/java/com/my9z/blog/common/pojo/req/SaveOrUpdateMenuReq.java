package com.my9z.blog.common.pojo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 新增或修改菜单req
 * @author: kim
 * @createTime: 2023-02-01  14:33
 */
@Data
public class SaveOrUpdateMenuReq {

    /**
     * id
     */
    private Long id;

    /**
     * 菜单名
     */
    @NotBlank(message = "菜单名不能为空")
    private String name;

    /**
     * icon
     */
    @NotBlank(message = "菜单icon不能为空")
    private String icon;

    /**
     * 路径
     */
    @NotBlank(message = "路径不能为空")
    private String path;

    /**
     * 组件
     */
    @NotBlank(message = "组件不能为空")
    private String component;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer orderNum;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 是否隐藏
     */
    private Boolean hidden;
}
