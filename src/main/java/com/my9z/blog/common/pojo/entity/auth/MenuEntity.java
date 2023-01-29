package com.my9z.blog.common.pojo.entity.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import com.my9z.blog.common.pojo.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * @description: 菜单实体类
 * @author: wczy9
 * @createTime: 2023-01-21  17:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_menu")
@EqualsAndHashCode(callSuper = false)
public class MenuEntity extends BaseEntity {

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
     * 排序
     */
    private Integer orderNum;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 是否隐藏
     */
    private Integer isHidden;

}

