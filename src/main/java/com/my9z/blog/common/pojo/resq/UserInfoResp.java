package com.my9z.blog.common.pojo.resq;

import lombok.Data;

import java.util.List;

/**
 * @description: 用户信息返回对象
 * @author: wczy9
 * @createTime: 2023-01-20  23:06
 */
@Data
public class UserInfoResp {

    /**
     * 用户信息id
     */
    private Long userInfoId;

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
     * 用户角色id
     */
    private List<Long> roleList;

}
