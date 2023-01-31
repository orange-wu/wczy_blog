package com.my9z.blog.config;


import cn.dev33.satoken.exception.NotLoginException;
import com.my9z.blog.common.enums.StatusCodeEnum;
import com.my9z.blog.common.exception.BusinessException;
import com.my9z.blog.common.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @description: 全局异常处理
 * @author: wczy9
 * @createTime: 2023-01-20  15:09
 */
@RestControllerAdvice
public class ExceptionAdviceConfig {

    /**
     * 处理服务异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = BusinessException.class)
    public Result<?> errorHandler(BusinessException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理服务异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = NotLoginException.class)
    public Result<?> errorHandler(NotLoginException e) {
        return Result.fail(StatusCodeEnum.NO_LOGIN);
    }

    /**
     * 处理系统异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public Result<?> errorHandler(Exception e) {
        e.printStackTrace();
        return Result.fail(StatusCodeEnum.SYSTEM_ERROR);
    }

}
