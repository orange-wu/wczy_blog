package com.my9z.blog.config.permissions;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.my9z.blog.common.pojo.entity.auth.ResourceEntity;
import com.my9z.blog.common.util.UserUtil;
import com.my9z.blog.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: sa-token拦截器，开启注解鉴权功能
 * @author: wczy9
 * @createTime: 2023-02-28  17:34
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //可以匿名访问的接口资源
        List<ResourceEntity> anonymousResourceList = resourceMapper.selectList(new LambdaQueryWrapper<ResourceEntity>()
                .eq(ResourceEntity::getAnonymous, Boolean.TRUE));
        List<String> anonymousUrlList = anonymousResourceList.stream().map(ResourceEntity::getUrl)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());
        // 注册Sa-Token拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor(handler -> UserUtil.checkLogin())) //添加登陆拦截器
                .addPathPatterns("/**")
                .excludePathPatterns(anonymousUrlList); //匿名访问的接口排除 不进行拦截
    }
}
