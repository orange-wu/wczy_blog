package com.my9z.blog.common.entity;

import com.my9z.blog.common.enums.StatusCodeEnum;
import lombok.Data;

/**
 * @description: 接口统一返回对象
 * @author: wczy9
 * @createTime: 2023-01-19  23:25
 */
@Data
public class Result<T> {

    /**
     * 返回状态
     */
    private Boolean flag;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public static <T> Result<T> ok() {
        return restResult(true, null, StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getDesc());
    }

    public static <T> Result<T> ok(T data) {
        return restResult(true, data, StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getDesc());
    }

    public static <T> Result<T> fail() {
        return restResult(false, null, StatusCodeEnum.FAIL.getCode(), StatusCodeEnum.FAIL.getDesc());
    }

    public static <T> Result<T> fail(StatusCodeEnum statusCodeEnum) {
        return restResult(false, null, statusCodeEnum.getCode(), statusCodeEnum.getDesc());
    }

    public static <T> Result<T> fail(T data) {
        return restResult(false, data, StatusCodeEnum.FAIL.getCode(), StatusCodeEnum.FAIL.getDesc());
    }

    private static <T> Result<T> restResult(Boolean flag, T data, Integer code, String message) {
        Result<T> apiResult = new Result<>();
        apiResult.setFlag(flag);
        apiResult.setData(data);
        apiResult.setCode(code);
        apiResult.setMessage(message);
        return apiResult;
    }

}
