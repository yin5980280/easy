package cn.org.easysite.framework.exception;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-08-12 13:36
 * @Description :
 * @Copyright : Copyright (c) 2019
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.framework.exception.ErrorCode
 */
public interface ErrorCode {

    /**
     * 获得错误码
     * @return
     */
    String getErrorCode();

    /**
     * 获得错误消息
     * @return
     */
    String getMessage();
}
