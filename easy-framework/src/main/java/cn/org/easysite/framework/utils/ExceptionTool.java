package cn.org.easysite.framework.utils;

import org.apache.commons.lang3.StringUtils;

import cn.org.easysite.framework.exception.BaseException;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-05-28 13:13
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.framework.utils.ExceptionTool
 */
public class ExceptionTool {

    //连接错误信息的字符
    private static final char JOIN_ERR_INFO_CHAR = ' ';

    //空字符串
    private static final String EMPTY_STRING = "";

    public static void createBaseException(String message) {
        throw new BaseException(message);
    }

    public static void createBaseException(int errorCode, String message) {
        throw new BaseException(errorCode, message);
    }

    public static void assertTrue(boolean expire, String message) {
        if (!expire) {
           createBaseException(message);
        }
    }

    public static void assertTrue(boolean expire, int errorCode, String message) {
        if (!expire) {
            createBaseException(errorCode, message);
        }
    }

    public static void assertNull(Object obj, String message) {
        if (null != obj) {
            createBaseException(message);
        }
    }

    public static void assertNull(Object obj, int errorCode, String message) {
        if (null != obj) {
            createBaseException(errorCode, message);
        }
    }

    public static void assertNotNull(Object obj, String message) {
        if (null == obj) {
            createBaseException(message);
        }
    }

    public static void assertNotNull(Object obj, int errorCode, String message) {
        if (null == obj) {
            createBaseException(errorCode, message);
        }
    }

    public static void assertNotBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            createBaseException(message);
        }
    }

    public static void assertNotBlank(String str, int errorCode, String message) {
        if (StringUtils.isBlank(str)) {
            createBaseException(errorCode, message);
        }
    }

}
