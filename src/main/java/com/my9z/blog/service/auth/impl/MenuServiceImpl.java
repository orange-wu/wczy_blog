package com.my9z.blog.service.auth.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my9z.blog.common.pojo.entity.auth.MenuEntity;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
import com.my9z.blog.common.pojo.resq.UserMenuResp;
import com.my9z.blog.common.util.UserUtil;
import com.my9z.blog.mapper.MenuMapper;
import com.my9z.blog.mapper.RoleMapper;
import com.my9z.blog.mapper.UserAuthMapper;
import com.my9z.blog.service.auth.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 菜单service实现类
 * @author: wczy9
 * @createTime: 2023-01-21  17:37
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<UserMenuResp> listUserMenus() {
        //1、拿到登陆用户的角色信息
        Long userId = UserUtil.getLoginId();
        //没有登陆
        if (userId == null) return null;
        UserAuthEntity userAuth = userAuthMapper.selectById(userId);
        if (userAuth == null || CollUtil.isEmpty(userAuth.getRoleIds())) return null;// TODO: 2023/1/24 用户信息异常
        List<Long> roleIds = userAuth.getRoleIds();
        //该用户没有任何角色
        if (CollUtil.isEmpty(roleIds)) return null;
        //2、拿到对应角色拥有的目录权限id
        List<RoleEntity> roles = roleMapper.selectBatchIds(roleIds);
        Set<Long> menuIds = roles.stream()
                .flatMap(role -> role.getMenuIds().stream())
                .collect(Collectors.toSet());
        //用户对应的角色没有任何权限
        if (CollUtil.isEmpty(menuIds)) return null;
        //3、根据目录id封装返回结果
        List<MenuEntity> menus = baseMapper.selectBatchIds(menuIds);
        // TODO: 2023/1/29 父子级递归返回
        return null;
    }
}