package com.my9z.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
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

}
