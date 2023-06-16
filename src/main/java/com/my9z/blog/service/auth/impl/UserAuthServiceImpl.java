package com.my9z.blog.service.auth.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my9z.blog.common.enums.ErrorCodeEnum;
import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
import com.my9z.blog.common.pojo.req.SearchUserReq;
import com.my9z.blog.common.pojo.req.UpdateUserRoleReq;
import com.my9z.blog.common.pojo.resp.UserPageInfoResp;
import com.my9z.blog.common.util.PageUtil;
import com.my9z.blog.common.util.UserUtil;
import com.my9z.blog.mapper.UserAuthMapper;
import com.my9z.blog.service.auth.SystemAuthService;
import com.my9z.blog.service.auth.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 用户账号service实现类
 * @author: wczy9
 * @createTime: 2023-01-20  14:03
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuthEntity> implements UserAuthService {

    @Autowired
    private SystemAuthService systemAuthService;

    @Override
    public WPage<UserPageInfoResp> pageUsers(SearchUserReq searchUserReq) {
        Page<UserPageInfoResp> page = new Page<>(searchUserReq.getPageNumber(), searchUserReq.getPageSize());
        Page<UserPageInfoResp> userPageInfoRespPage = baseMapper.selectUserPageInfo(page, searchUserReq);
        return PageUtil.convert(userPageInfoRespPage);
    }

    @Override
    public void updateUserRole(UpdateUserRoleReq updateUserRoleReq) {
        //构建修改的用户数据
        UserAuthEntity newUserAuth = BeanUtil.copyProperties(updateUserRoleReq, UserAuthEntity.class);
        //找到现有用户
        UserAuthEntity oldUserAuth = baseMapper.selectById(updateUserRoleReq.getId());
        if (oldUserAuth == null) {
            throw ErrorCodeEnum.USER_DATA_IS_NOT_EXIST.buildException();
        }
        //昵称是否修改
        boolean nickNameUpdate = StrUtil.equals(newUserAuth.getNickname(), oldUserAuth.getNickname());
        //禁用状态是否修改
        boolean disableUpdate = ObjectUtil.equals(newUserAuth.getDisable(), oldUserAuth.getDisable());
        //角色是否修改
        boolean roleUpdate = ObjectUtil.equals(newUserAuth.getRoleIds(), oldUserAuth.getRoleIds());
        //有一个字段改动则需要修改
        if (nickNameUpdate | disableUpdate | roleUpdate) baseMapper.updateById(newUserAuth);
        //如果是用户被禁用则强制下线
        if (oldUserAuth.getDisable() == Boolean.FALSE && newUserAuth.getDisable() == Boolean.TRUE)
            UserUtil.logout(CollUtil.newArrayList(updateUserRoleReq.getId()));
        //判断是否需要更改权限缓存
        Set<Long> updateRoleSet = new HashSet<>();
        if (disableUpdate || roleUpdate) {
            //用户角色缓存删除
            systemAuthService.deleteUserRoleCache(CollUtil.newArrayList(updateUserRoleReq.getId()));
        }
    }

    @Override
    public List<String> selectRoleLabelByUserId(Long userId) {
        if (userId == null) return null;
        return baseMapper.selectRoleLabelByUserId(userId);
    }
}