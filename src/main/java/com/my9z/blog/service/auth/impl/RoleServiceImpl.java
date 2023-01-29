package com.my9z.blog.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
import com.my9z.blog.mapper.RoleMapper;
import com.my9z.blog.service.auth.RoleService;
import org.springframework.stereotype.Service;

/**
 * @description: 角色service实现类
 * @author: wczy9
 * @createTime: 2023-01-29  21:26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
}