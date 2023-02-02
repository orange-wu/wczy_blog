package com.my9z.blog.common.pojo.entity.auth;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.my9z.blog.common.pojo.entity.BaseEntity;
import com.my9z.blog.config.mptypehandler.LongListToJsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 角色实体类
 * @author: wczy9
 * @createTime: 2023-01-29  21:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_role",autoResultMap = true)
@EqualsAndHashCode(callSuper = false)
public class RoleEntity extends BaseEntity {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleLabel;

    /**
     * 是否禁用
     */
    private Boolean disable;

    /**
     * 菜单id列表
     */
    @TableField(value = "menu_ids", typeHandler = LongListToJsonTypeHandler.class)
    private List<Long> menuIds;

    /**
     * 资源接口id列表
     */
    @TableField(value = "resource_ids", typeHandler = LongListToJsonTypeHandler.class)
    private List<Long> resourceIds;

}
