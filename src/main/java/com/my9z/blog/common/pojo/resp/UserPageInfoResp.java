package com.my9z.blog.common.pojo.resp;

import com.my9z.blog.common.pojo.dto.RoleIdDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 用户分页信息返回对象
 * @author: wczy9
 * @createTime: 2023-03-16  14:06
 */
@Data
public class UserPageInfoResp {

    /**
     * id
     */
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户角色
     */
    private List<RoleIdDto> roleList;

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 用户评论状态
     */
    private Boolean disable;

}