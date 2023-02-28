package com.my9z.blog.common.pojo.resp;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 接口资源返回对象
 * @author: kim
 * @createTime: 2023-02-02  16:44
 */
@Data
public class ResourceResp {

    /**
     * 接口
     */
    private Long id;

    /**
     * 父模块id
     */
    private Long parentId;

    /**
     * 模块名
     */
    private String modularName;

    /**
     * 接口名
     */
    private String resourceName;

    /**
     * 接口路径
     */
    private String url;

    /**
     * 权限名
     */
    private String permission;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 是否匿名访问
     */
    private Boolean anonymous;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
