package com.my9z.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
import com.my9z.blog.common.pojo.req.SearchUserReq;
import com.my9z.blog.common.pojo.resp.UserPageInfoResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 用户账号mapper
 * @author: wczy9
 * @createTime: 2023-01-20  14:01
 */
@Repository
public interface UserAuthMapper extends BaseMapper<UserAuthEntity> {

    /**
     * 通过角色id查询用户列表
     *
     * @param roleId 角色id
     * @return 用户列表
     */
    List<UserAuthEntity> selectUsrByRoleId(@Param("roleId") Long roleId);

    /**
     * 分页查询用户信息
     *
     * @param page  分页信息
     * @param param 查询参数
     * @return 用户数据分页信息
     */
    Page<UserPageInfoResp> selectUserPageInfo(@Param(("page")) Page<UserPageInfoResp> page,
                                              @Param("param") SearchUserReq param);

    /**
     * 查询用户的角色类型
     *
     * @param userId 用户id
     * @return 角色类型
     */
    List<String> selectRoleLabelByUserId(@Param("userId") Long userId);
}