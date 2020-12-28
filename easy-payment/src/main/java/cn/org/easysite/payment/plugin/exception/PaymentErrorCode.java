/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.payment.plugin.exception;

import cn.org.easysite.framework.exception.ErrorCode;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/12/28 3:38 下午
 * @link : cn.org.easysite.payment.plugin.exception.PaymentErrorCode
 */
public enum PaymentErrorCode implements ErrorCode {

    /**
     * 支付网关未找到插件实现类
     */
    PAYMENT_PAY_CHANNEL_PLUGIN_NOT_FOUND_ERROR("10005", "未找到该渠道的插件实现类"),

    /**
     * 未在当前插件下找到该支付方式的实现
     */
    PAYMENT_PAY_CHANNEL_PLUGIN_PAY_TYPE_NOT_FOUND_ERROR("10006", "未在当前插件下找到该支付方式的实现"),
    ;

    private String errorCode;

    private String message;

    PaymentErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
