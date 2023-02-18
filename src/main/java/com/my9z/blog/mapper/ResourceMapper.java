package com.my9z.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my9z.blog.common.pojo.entity.auth.ResourceEntity;
import com.my9z.blog.common.pojo.resp.ResourceResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 接口资源mapper
 * @author: kim
 * @createTime: 2023-02-02  16:26
 */
@Repository
public interface ResourceMapper extends BaseMapper<ResourceEntity> {

    /**
     * 分页查询接口资源
     *
     * @param page         分页参数
     * @param parentIdList 模块id集合
     * @return 接口资源分页数据
     */
    Page<ResourceResp> resourceRespPage(@Param(("page")) Page<ResourceResp> page,
                                        @Param("parentIdList") List<Long> parentIdList);

}
