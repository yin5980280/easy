package com.vartime.easy.framework.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-30 10:18
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.vartime.easy.framework.exception.BaseException
 */
@Data
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private String message;

    private Integer code;

    public BaseException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
