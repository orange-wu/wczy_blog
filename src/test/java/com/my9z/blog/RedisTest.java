package com.my9z.blog;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.my9z.blog.common.constant.RedisKeyConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @description: redis测试
 * @author: wczy9
 * @createTime: 2023-03-01  10:57
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testGet(){
        RKeys keys = redissonClient.getKeys();
        log.info("keys:{}", JSONUtil.toJsonStr(keys.getKeys()));
        RBucket<String> wcz = redissonClient.getBucket("wcz");
        wcz.set("czy");
        log.info("wcz:{}", wcz.get());
    }

    @Test
    public void testMap() {
        String userPermissionKey = RedisKeyConstant.getRoleAuthKey();
        RMap<String, List<String>> userPermissionCache = redissonClient.getMap(userPermissionKey);
        log.info("userPermissionCache:{}", JSON.toJSONString(userPermissionCache));
    }

}