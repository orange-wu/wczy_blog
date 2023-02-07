package com.my9z.blog.controller.admin;

import com.my9z.blog.common.pojo.Result;
import com.my9z.blog.common.pojo.resq.ModularResp;
import com.my9z.blog.common.pojo.resq.ResourceResp;
import com.my9z.blog.service.auth.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @return 接口资源列表
     */
    @GetMapping("/resources")
    public Result<List<ResourceResp>> listMenus(Long parentId) {
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

}
