package com.my9z.blog.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;

import java.util.List;

/**
 * @description: 用户账号service
 * @author: wczy9
 * @createTime: 2023-01-20  14:02
 */
public interface UserAuthService extends IService<UserAuthEntity> {


    /**
     * 获取用户的权限码集合
     * @param userId 用户id
     * @return 权限码集合
     */
    List<String> userPermissionList(Long userId);

}