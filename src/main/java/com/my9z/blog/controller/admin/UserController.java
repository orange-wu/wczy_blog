package com.my9z.blog.controller.admin;

import com.alibaba.fastjson.JSON;
import com.my9z.blog.common.entity.Result;
import com.my9z.blog.common.entity.dto.LoginUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 用户相关接口
 * @author: wczy9
 * @createTime: 2023-01-20  12:09
 */
@Slf4j
@RestController
public class UserController {

    @PostMapping("/login")
    public Result<?> login(LoginUserDTO loginUser){
        log.info(JSON.toJSONString(loginUser));
        return Result.ok();
    }

}