package com.my9z.blog.config;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.hutool.core.util.StrUtil;
import com.my9z.blog.common.enums.StatusCodeEnum;
import com.my9z.blog.common.exception.BusinessException;
import com.my9z.blog.common.pojo.Result;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


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
     * 未登录异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = NotLoginException.class)
    public Result<?> errorHandler(NotLoginException e) {
        return Result.fail(StatusCodeEnum.NO_LOGIN);
    }

    /**
     * 没权限异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = NotPermissionException.class)
    public Result<?> errorHandler(NotPermissionException e) {
        return Result.fail(StatusCodeEnum.AUTHORIZED);
    }

    /**
     * 处理请求参数不合理异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<?> errorHandler(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        String errorMsg = StrUtil.EMPTY;
        if (result.hasErrors()) {
            errorMsg = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(StrUtil.COMMA));
        }
        errorMsg = StrUtil.isEmpty(errorMsg) ? StatusCodeEnum.VALID_ERROR.getDesc() : errorMsg;
        return Result.fail(StatusCodeEnum.VALID_ERROR.getCode(), errorMsg);
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
