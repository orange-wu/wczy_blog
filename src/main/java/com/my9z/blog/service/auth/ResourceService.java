package com.my9z.blog.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my9z.blog.common.pojo.entity.auth.ResourceEntity;
import com.my9z.blog.common.pojo.resq.ModularResp;
import com.my9z.blog.common.pojo.resq.ResourceResp;

import java.util.List;

/**
 * @description: 接口资源service
 * @author: kim
 * @createTime: 2023-02-02  16:39
 */
public interface ResourceService extends IService<ResourceEntity> {

    /**
     * 根据接口模块id查询接口列表
     *
     * @param parentId 接口模块id
     * @return 接口资源列表
     */
    List<ResourceResp> listResources(Long parentId);

    /**
     * 查询接口资源的所以模块信息
     * @return 模块信息
     */
    List<ModularResp> listModular();
}
