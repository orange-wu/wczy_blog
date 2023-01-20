package com.my9z.blog.service.admin.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.my9z.blog.common.enums.ErrorCodeEnum;
import com.my9z.blog.common.pojo.dto.LoginUserDTO;
import com.my9z.blog.common.pojo.entity.user.UserAuthEntity;
import com.my9z.blog.common.pojo.resq.UserInfoResp;
import com.my9z.blog.mapper.UserAuthMapper;
import com.my9z.blog.service.admin.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: admin后台用户相关操作service实现类
 * @author: wczy9
 * @createTime: 2023-01-20  14:19
 */
@Slf4j
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public UserInfoResp login(LoginUserDTO loginUser) {
        // TODO: 2023/1/21 当前账号已登陆怎么办
        //根据用户名、密码查询用户账号
        UserAuthEntity userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuthEntity>()
                .eq(UserAuthEntity::getUsername, loginUser.getUsername())
                .eq(UserAuthEntity::getPassword, loginUser.getPassword()));
        //找不到对应账号
        if (userAuth == null) throw ErrorCodeEnum.LOGIN_USER_PASSWORD_ERROR.buildException();
        //对应账号被禁用
        if (userAuth.getDisable()) throw ErrorCodeEnum.USER_IS_DISABLE.buildException();
        //sa-token登陆通过cookie返回前端token
        StpUtil.login(userAuth.getUsername());
        //如果sa-token返回没有登陆则抛出登陆异常
        if (!StpUtil.isLogin()) throw ErrorCodeEnum.LOGIN_SA_TOKEN_ERROR.buildException();
        //组装返回信息
        UserInfoResp userInfoResp = concatResp(userAuth);
        log.info("userInfoResp:{}", JSON.toJSONString(userInfoResp));
        return userInfoResp;
    }

    private static UserInfoResp concatResp(UserAuthEntity userAuth) {
        UserInfoResp userInfoResp = new UserInfoResp();
        String tokenValue = StpUtil.getTokenInfo().tokenValue;
        userInfoResp.setToken(tokenValue);
        userInfoResp.setNickname(userAuth.getNickname());
        userInfoResp.setAvatar(userAuth.getAvatar());
        userInfoResp.setIntro(userAuth.getIntro());
        userInfoResp.setWebSite(userAuth.getWebSite());
        userInfoResp.setRoleIds(userAuth.getRoleIds());
        return userInfoResp;
    }
}