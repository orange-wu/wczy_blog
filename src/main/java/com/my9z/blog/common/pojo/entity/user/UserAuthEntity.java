package com.my9z.blog.common.pojo.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.my9z.blog.common.pojo.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 用户账号实体类
 * @author: wczy9
 * @createTime: 2023-01-20  13:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user_auth")
@EqualsAndHashCode(callSuper = false)
public class UserAuthEntity extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录类型
     */
    private Integer loginType;

    /**
     * 用户登录ip
     */
    private String ipAddress;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 邮箱号
     */
    private String email;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户简介
     */
    private String intro;

    /**
     * 个人网站
     */
    private String webSite;

    /**
     * 是否禁用
     */
    private Boolean disable;

    /**
     * 角色id列表
     */
    private List<Long> roleIds;

}
