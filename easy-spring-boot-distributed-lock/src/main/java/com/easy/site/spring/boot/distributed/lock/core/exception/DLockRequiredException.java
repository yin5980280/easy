package com.easy.site.spring.boot.distributed.lock.core.exception;

import com.easy.site.framework.exception.BaseException;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-03-14 23:13
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : com.easy.site.spring.boot.distributed.lock.core.exception.DLockRequiredException
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
