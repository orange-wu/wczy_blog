package com.my9z.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
import com.my9z.blog.common.pojo.resp.RoleResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 角色mapper
 * @author: wczy9
 * @createTime: 2023-01-29  21:18
 */
@Repository
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 查询有几个角色拥有menuId对应的菜单权限
     *
     * @param menuId 菜单id
     * @return RoleEntity 拥有该菜单权限的角色信息
     */
    List<RoleEntity> selectCountMenuId(@Param("menuId") Long menuId);

    /**
     * 查询有几个角色拥有resourceId对应的接口资源权限
     *
     * @param resourceId 接口资源id
     * @return RoleEntity 拥有该接口权限的角色信息
     */
    List<RoleEntity> selectCountResourceId(@Param("resourceId") Long resourceId);

    /**
     * 分页查询用户分页对象
     *
     * @param page     分页信息
     * @param roleName 用户名称
     * @return 用户信息分页数据
     */
    Page<RoleResp> roleRespPage(@Param(("page")) Page<RoleResp> page, @Param("roleName") String roleName);

    /**
     * 获取用户的角色集合
     *
     * @param userId 用户id
     * @return 角色集合
     */
    List<RoleEntity> userRoleList(@Param("userId") Long userId);
}
