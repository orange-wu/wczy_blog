package com.my9z.blog.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
import com.my9z.blog.mapper.UserAuthMapper;
import com.my9z.blog.service.auth.UserAuthService;
import org.springframework.stereotype.Service;

/**
 * @description: 用户账号service实现类
 * @author: wczy9
 * @createTime: 2023-01-20  14:03
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuthEntity> implements UserAuthService {

}