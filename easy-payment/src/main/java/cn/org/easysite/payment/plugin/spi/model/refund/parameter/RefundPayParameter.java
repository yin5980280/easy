/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.payment.plugin.spi.model.refund.parameter;

import java.math.BigDecimal;
import java.util.Map;

import cn.org.easysite.payment.plugin.spi.model.pay.parameter.PayParameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/12/27 10:55 上午
 * @link : cn.org.easysite.payment.plugin.spi.model.refund.parameter.PaymentPayRefundParameter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundPayParameter extends PayParameter {
    /**
     * 系统退款单号
     */
    private String refundOrderNo;

    /**
     * 渠道退款单号
     */
    private String channelRefundOrderNo;

    /**
     * 机构交易号
     */
    private String orgRefundTransactionNo;

    /**
     * 退款总额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 退款扩展参数
     */
    private Map<String, Object> refundExtraParams;



}
