package com.my9z.blog.service.admin.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.my9z.blog.common.enums.ErrorCodeEnum;
import com.my9z.blog.common.pojo.dto.LoginUserDTO;
import com.my9z.blog.common.pojo.entity.user.UserAuthEntity;
import com.my9z.blog.mapper.UserAuthMapper;
import com.my9z.blog.service.admin.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: admin后台用户相关操作service实现类
 * @author: wczy9
 * @createTime: 2023-01-20  14:19
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public void login(LoginUserDTO loginUser) {
        UserAuthEntity userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuthEntity>()
                .eq(UserAuthEntity::getUsername, loginUser.getUsername())
                .eq(UserAuthEntity::getPassword, loginUser.getPassword()));
        if (userAuth == null) throw ErrorCodeEnum.LOGIN_USER_PASSWORD_ERROR.buildException();
        StpUtil.login(userAuth.getId());
        if (!StpUtil.isLogin()) throw ErrorCodeEnum.LOGIN_SA_TOKEN_ERROR.buildException();
    }
}