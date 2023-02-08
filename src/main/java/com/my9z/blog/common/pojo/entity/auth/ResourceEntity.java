package com.my9z.blog.common.pojo.entity.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import com.my9z.blog.common.pojo.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @description: 接口资源实体类
 * @author: kim
 * @createTime: 2023-02-02  14:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_resource", autoResultMap = true)
@EqualsAndHashCode(callSuper = false)
public class ResourceEntity extends BaseEntity {

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
     * 请求方式
     */
    private String requestMethod;

    /**
     * 父模块id
     */
    private Long parentId;

    /**
     * 是否匿名访问
     */
    private Boolean anonymous;

    /**
     * 是否是模块
     */
    private Boolean modular = false;

}
