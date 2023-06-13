package com.my9z.blog.config.permissions;

import com.my9z.blog.service.auth.SystemAuthService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 系统启动时刷新一次权限缓存
 * @author: wczy9
 * @createTime: 2023-06-12  17:02
 */
@Component
public class RoleAuthCacheRefreshConfig implements InitializingBean {
    @Autowired
    private SystemAuthService systemAuthService;

    @Override
    public void afterPropertiesSet() {
        systemAuthService.refreshAuthAllCache();
    }

}