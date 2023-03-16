package com.my9z.blog.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
import com.my9z.blog.common.pojo.req.SearchUserReq;
import com.my9z.blog.common.pojo.resp.UserPageInfoResp;

import java.util.List;

/**
 * @description: 用户账号service
 * @author: wczy9
 * @createTime: 2023-01-20  14:02
 */
public interface UserAuthService extends IService<UserAuthEntity> {


    /**
     * 获取用户的权限码集合
     *
     * @param userId 用户id
     * @return 权限码集合
     */
    List<String> userPermissionList(Long userId);

    /**
     * 获取用户的角色集合
     *
     * @param userId 用户id
     * @return 角色集合
     */
    List<String> userRoleList(Long userId);


    /**
     * 分页查询用户信息
     *
     * @param searchUserReq 查询入惨
     * @return 用户数据分页信息
     */
    WPage<UserPageInfoResp> pageUsers(SearchUserReq searchUserReq);

}