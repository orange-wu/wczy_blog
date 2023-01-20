package com.my9z.blog.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: 自封装相关异常
 * @author: wczy
 * @createTime: 2023-01-20  15:02
 */
@Getter
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    public Integer code;
    public String message;

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
