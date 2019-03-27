package com.easy.site.framework.exception;

import com.easy.site.framework.utils.MessageUtils;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-30 10:18
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : com.easy.site.framework.exception.BaseException
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class BaseException extends RuntimeException {

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误消息码
     */
    private String messageCode;

    /**
     * 错误码
     */
    private int code = 500;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public BaseException(int code, String message) {
        this(null, null, 500, null, message);
    }

    public BaseException(String module, String messageCode, Object[] args, String defaultMessage) {
        this(module, messageCode, 500 , args, defaultMessage);

    }

    public BaseException(String module, String messageCode, Object[] args) {
        this(module, messageCode, args, null);
    }

    public BaseException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public BaseException(String messageCode, Object[] args)
    {
        this(null, messageCode, args, null);
    }

    public BaseException(String defaultMessage)
    {
        this(null, null, null, defaultMessage);
    }

    @Override
    public String getMessage() {
        String message = null;
        if (!StringUtils.isEmpty(messageCode)) {
            message = MessageUtils.message(messageCode, args);
        }
        if (message == null) {
            message = defaultMessage;
        }
        return message;
    }

    /**
     * 从配置中获取
     * @return
     */
    public int getCode() {
        String codeStr = null;
        if (!StringUtils.isEmpty(messageCode)) {
            codeStr = MessageUtils.message(messageCode + ".code", args);
        }
        if (codeStr == null) {
            return code;
        } else {
            try {
                return Integer.parseInt(codeStr);
            } catch (Exception e) {
                return code;
            }
        }
    }

}
