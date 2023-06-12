package com.my9z.blog.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
import com.my9z.blog.common.pojo.req.SearchUserReq;
import com.my9z.blog.common.pojo.req.UpdateUserRoleReq;
import com.my9z.blog.common.pojo.resp.UserPageInfoResp;

/**
 * @description: 用户账号service
 * @author: wczy9
 * @createTime: 2023-01-20  14:02
 */
public interface UserAuthService extends IService<UserAuthEntity> {

    /**
     * 分页查询用户信息
     *
     * @param searchUserReq 查询入参
     * @return 用户数据分页信息
     */
    WPage<UserPageInfoResp> pageUsers(SearchUserReq searchUserReq);

    /**
     * 修改用户的角色和昵称
     *
     * @param updateUserRoleReq 修改入参
     */
    void updateUserRole(UpdateUserRoleReq updateUserRoleReq);
}