package com.vartime.easy.spring.boot.distributed.lock.core.exception;

import com.vartime.easy.framework.exception.BaseException;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-03-14 23:13
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.vartime.easy.spring.boot.distributed.lock.core.exception.DLockRequiredException
 */
public class DLockRequiredException extends BaseException {

    public DLockRequiredException() {
    }

    public DLockRequiredException(int code, String message) {
        super(code, message);
    }

    public DLockRequiredException(String defaultMessage) {
        super(defaultMessage);
    }
}
