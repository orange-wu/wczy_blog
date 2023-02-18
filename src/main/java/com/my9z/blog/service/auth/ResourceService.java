package com.my9z.blog.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.entity.auth.ResourceEntity;
import com.my9z.blog.common.pojo.req.SaveOrUpdateResourceReq;
import com.my9z.blog.common.pojo.req.SearchResourceReq;
import com.my9z.blog.common.pojo.resp.ResourceTreeResp;
import com.my9z.blog.common.pojo.resp.ModularResp;
import com.my9z.blog.common.pojo.resp.ResourceResp;

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
     * @param searchResourceReq 查询请求对象
     * @return 接口资源列表
     */
    WPage<ResourceResp> listResources(SearchResourceReq searchResourceReq);

    /**
     * 查询接口资源的所以模块信息
     *
     * @return 模块信息
     */
    List<ModularResp> listModular();

    /**
     * 根据id删除接口资源
     * 如果存在用户拥有对应的接口资源的权限 则不允许删除
     */
    void deleteResource(Long resourceId);

    /**
     * 根据id字段新增或修改接口资源
     *
     * @param resourceReq 接口资源请求对象
     */
    void saveOrUpdateResource(SaveOrUpdateResourceReq resourceReq);

    /**
     * 树形结构返回接口资源数据 不返回匿名访问的接口资源
     *
     * @return 模块-接口集合 模式的接口资源数据
     */
    List<ResourceTreeResp> listModularResource();
}
