package com.my9z.blog.service.auth.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
import com.my9z.blog.common.pojo.req.SearchUserReq;
import com.my9z.blog.common.pojo.req.UpdateUserRoleReq;
import com.my9z.blog.common.pojo.resp.UserPageInfoResp;
import com.my9z.blog.common.util.PageUtil;
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

    @Override
    public WPage<UserPageInfoResp> pageUsers(SearchUserReq searchUserReq) {
        Page<UserPageInfoResp> page = new Page<>(searchUserReq.getPageNumber(), searchUserReq.getPageSize());
        Page<UserPageInfoResp> userPageInfoRespPage = baseMapper.selectUserPageInfo(page, searchUserReq);
        return PageUtil.convert(userPageInfoRespPage);
    }

    @Override
    public void updateUserRole(UpdateUserRoleReq updateUserRoleReq) {
        UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setId(updateUserRoleReq.getId());
        userAuthEntity.setNickname(updateUserRoleReq.getNickname());
        userAuthEntity.setDisable(updateUserRoleReq.getDisable());
        userAuthEntity.setRoleIds(updateUserRoleReq.getRoleIdList());
        baseMapper.updateById(userAuthEntity);
    }
}