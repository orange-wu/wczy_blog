package com.my9z.blog.controller.admin;

import com.my9z.blog.common.entity.Result;
import com.my9z.blog.common.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 博客信息
 * @author: wczy9
 * @createTime: 2023-01-19  23:22
 */
@Slf4j
@RestController
public class BlogInfoController {

    @Resource
    private HttpServletRequest request;

    @PostMapping("/report")
    public Result<?> report() {
        String ipAddress = RequestUtil.getIpAddress(request);
        String browserName = RequestUtil.getBrowserName(request);
        String operatingSystemName = RequestUtil.getOperatingSystemName(request);
        String ipSource = RequestUtil.getIpSource(ipAddress);
        log.info("ipAddress:{},browserName:{},operatingSystemName:{},ipSource:{}",ipAddress,browserName,operatingSystemName,ipSource);
        return Result.ok();
    }

}