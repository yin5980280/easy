package cn.org.easysite.payment.plugin.spi.model.refund.result;

import java.math.BigDecimal;
import java.util.Date;

import cn.org.easysite.payment.plugin.spi.model.base.result.PaymentBaseResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019/7/23 4:57 PM
 * @Description : 退款返回结果实体
 * @Copyright : Copyright (c) 2018
 * @Company : IOT Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.model.refund.result.PaymentRefundResult
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayRefundResult extends PaymentBaseResult {
    /**
     * 退款单号
     */
    private String refundOrderNo;
    /**
     * 退款金额，单位分
     */
    private BigDecimal refundAmount;

    /**
     * 商户退款金额，单位分
     */
    private BigDecimal actualRefundAmount;

    private BigDecimal couponRefundAmount;

    /**
     * 退款状态 1- 退款中 2-退款成功 3-退款失败
     */
    private Integer refundStatus;

    /**
     * 第三方退款单号
     */
    private String channelRefundOrderNo;

    /**
     * 退款时间
     */
    private Date refundTime;

}
