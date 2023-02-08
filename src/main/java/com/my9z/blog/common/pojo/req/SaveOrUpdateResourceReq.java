package com.my9z.blog.common.pojo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 新增或修改接口资源请求对象
 * @author: kim
 * @createTime: 2023-02-08  09:20
 */
@Data
public class SaveOrUpdateResourceReq {

    /**
     * 接口id
     */
    private Long id;

    /**
     * 父模块id
     */
    @NotNull(message = "模块名不能为空")
    private Long parentId;

    /**
     * 接口名
     */
    @NotBlank(message = "接口名不能为空")
    private String resourceName;

    /**
     * 接口路径
     */
    @NotBlank(message = "接口路径不能为空")
    private String url;

    /**
     * 请求方式
     */
    @NotBlank(message = "请求方式不能为空")
    private String requestMethod;

    /**
     * 是否匿名访问
     */
    private Boolean anonymous;
}
