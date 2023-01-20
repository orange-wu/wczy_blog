package com.my9z.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my9z.blog.common.pojo.entity.user.UserAuthEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 用户账号mapper
 * @author: wczy9
 * @createTime: 2023-01-20  14:01
 */
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuthEntity> {
}