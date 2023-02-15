package com.my9z.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
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

}