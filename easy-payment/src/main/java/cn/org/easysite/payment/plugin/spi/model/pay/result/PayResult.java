package cn.org.easysite.payment.plugin.spi.model.pay.result;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.org.easysite.payment.plugin.spi.model.base.result.PaymentBaseResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 盘达天神
 * @version : 1.0
 * @date : 2020/2/14 10:19 上午
 * @Description :
 * @Copyright : Copyright (c) 2019
 * @Company : EasySite Technology 阿富汗 Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.model.pay.result.PaymentPayResult
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayResult extends PaymentBaseResult {
    /**
     * 支付状态：1未支付，3支付成功，9支付失败
     */
    private Integer payStatus;
    /**
     * 支付单号
     */
    private String payOrderNo;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 支付平台交易号（支付宝/微信返回）
     */
    private String orgTransactionNo;

    /**
     * 支付渠道支付单号
     */
    private String channelPayOrderNo;

    /**
     * 支付金额 单位（元）
     */
    private BigDecimal payAmount;

    /**
     * 实际支付金额 单位（元）
     */
    private BigDecimal actual;

    /**
     * 支付平台减免金额 单位（元）
     */
    private BigDecimal coupon;

    /**
     * 商户实际收款金额 单位（元）
     */
    private BigDecimal receipt;

    /**
     * 手续费 单位（元）
     */
    private BigDecimal serviceFee;

    /**
     * 收款账户(暂时没用)
     */
    private String payeeAccount;

    /**
     * 第三方平台卖家用户id
     */
    private String payeeOpenid;

    /**
     * 第三方平台用户id，支付宝16位，微信128位
     */
    private String payerOpenid;

    /**
     * 支付账号
     */
    private String payerAccount;

    /**
     * 第三方优惠信息列表
     */
    private List<PayThirdCouponResult> coupons;

    /**
     * 第三方货币单位
     */
    private String thirdCurrency;

    private Map<String, Object> payResultExtraParams;

}
