package com.my9z.blog.controller.admin;

import com.my9z.blog.common.pojo.Result;
import com.my9z.blog.common.pojo.req.SaveOrUpdateResourceReq;
import com.my9z.blog.common.pojo.resq.ModularResp;
import com.my9z.blog.common.pojo.resq.ResourceResp;
import com.my9z.blog.service.auth.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @description: 接口资源相关接口
 * @author: kim
 * @createTime: 2023-02-02  17:30
 */
@Slf4j
@RestController()
@RequestMapping("/admin")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 获取接口资源列表
     *
     * @param parentId 模块名id集合 逗号分割
     * @return 接口资源列表
     */
    @GetMapping("/resources")
    public Result<List<ResourceResp>> listMenus(String parentId) {
        return Result.ok(resourceService.listResources(parentId));
    }

    /**
     * 获取接口模块列表
     *
     * @return 接口模块列表
     */
    @GetMapping("/modular")
    public Result<List<ModularResp>> listModular() {
        return Result.ok(resourceService.listModular());
    }

    /**
     * 删除接口资源
     *
     * @param resourceId 接口资源id
     * @return {@link Result<>}
     */
    @DeleteMapping("/resources/{resourceId}")
    public Result<?> deleteResource(@PathVariable("resourceId") Long resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.ok();
    }

    /**
     * 新增或修改资源 id为空认为是新增数据
     *
     * @param resourceReq 接口资源信息
     * @return {@link Result<>}
     */
    @PostMapping("/resources")
    public Result<?> saveOrUpdateResource(@RequestBody @Valid SaveOrUpdateResourceReq resourceReq) {
        resourceService.saveOrUpdateResource(resourceReq);
        return Result.ok();
    }

}
