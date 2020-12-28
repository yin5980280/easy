/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.payment.plugin.exception;


import cn.org.easysite.framework.exception.BaseException;
import cn.org.easysite.framework.exception.ErrorCode;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/12/27 12:58 下午
 * @link : cn.org.easysite.payment.plugin.exception.PaymentException
 */
public class PaymentException extends BaseException {

    public PaymentException(ErrorCode errorCode) {
        super(errorCode);
    }
    public PaymentException(String code, Object[] args) {
        super("payment", code, args);
    }

    public PaymentException(String defaultMessage) {
        super(defaultMessage);
    }
}
