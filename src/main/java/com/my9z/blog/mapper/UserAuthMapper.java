package com.my9z.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
import org.springframework.stereotype.Repository;

/**
 * @description: 用户账号mapper
 * @author: wczy9
 * @createTime: 2023-01-20  14:01
 */
@Repository
public interface UserAuthMapper extends BaseMapper<UserAuthEntity> {
}