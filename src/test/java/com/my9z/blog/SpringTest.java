package com.my9z.blog;

import com.alibaba.fastjson.JSON;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
import com.my9z.blog.mapper.RoleMapper;
import com.my9z.blog.service.auth.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description: 测试类
 * @author: wczy9
 * @createTime: 2023-01-28  10:45
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SpringTest {

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @Test
    public void roleIdMenuList(){

        RoleEntity roleEntity = roleService.getById(1619701068390735874L);
        log.info("sss:{}", JSON.toJSONString(roleEntity));

//        RoleEntity roleDto = new RoleEntity();
//        roleDto.setRoleName("aaa");
//        roleDto.setRoleLabel("aaa");
//        roleDto.setDisable(true);
//        roleDto.setMenuIds(Lists.list(1L,2L));
//        roleMapper.insert(roleDto);
    }

}