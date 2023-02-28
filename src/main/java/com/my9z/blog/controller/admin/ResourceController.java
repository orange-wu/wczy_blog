package com.my9z.blog.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.my9z.blog.common.pojo.Result;
import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.req.SaveOrUpdateResourceReq;
import com.my9z.blog.common.pojo.req.SearchResourceReq;
import com.my9z.blog.common.pojo.resp.ModularResp;
import com.my9z.blog.common.pojo.resp.ResourceResp;
import com.my9z.blog.common.pojo.resp.ResourceTreeResp;
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
     * 分页获取接口资源列表
     *
     * @param searchResourceReq 分页查询请求对象
     * @return 接口资源列表
     */
    @GetMapping("/resources")
    @SaCheckPermission(value = "resources-list", orRole = "admin")
    public Result<WPage<ResourceResp>> listMenus(SearchResourceReq searchResourceReq) {
        return Result.ok(resourceService.listResources(searchResourceReq));
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

    /**
     * 角色管理界面接口资源数据接口
     *
     * @return 接口资源树形结构
     */
    @GetMapping("role/resources")
    public Result<List<ResourceTreeResp>> listModularResource() {
        return Result.ok(resourceService.listModularResource());
    }

}
