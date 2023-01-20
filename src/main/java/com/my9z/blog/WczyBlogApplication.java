package com.my9z.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: wczy_blog启动类
 * @author: wczy9
 * @createTime: 2023-01-19  17:54
 */
@MapperScan("com.my9z.blog.mapper")
@SpringBootApplication
public class WczyBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(WczyBlogApplication.class, args);
    }
}