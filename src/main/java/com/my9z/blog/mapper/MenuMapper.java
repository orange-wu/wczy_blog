package com.my9z.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my9z.blog.common.pojo.entity.auth.MenuEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 菜单mapper
 * @author: wczy9
 * @createTime: 2023-01-21  17:35
 */
@Repository
public interface MenuMapper extends BaseMapper<MenuEntity> {

    /**
     * 查询子菜单id
     *
     * @param menuId 菜单id
     * @return {@link List <>} 子菜单id集合
     */
    List<Long> selectChildId(@Param("menuId") Long menuId);

    /**
     * 通过id集合批量删除
     *
     * @param ids id集合
     */
    void deleteByIds(List<Long> ids);
}
