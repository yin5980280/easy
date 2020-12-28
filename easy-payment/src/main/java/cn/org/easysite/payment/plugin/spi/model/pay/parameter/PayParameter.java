/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.payment.plugin.spi.model.pay.parameter;

import java.math.BigDecimal;
import java.util.Map;

import cn.org.easysite.payment.plugin.spi.model.base.parameter.PayeePayParameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/12/27 10:51 上午
 * @link : cn.org.easysite.payment.plugin.spi.model.pay.parameter.PaymentPayParameter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayParameter extends PayeePayParameter {
    /**
     * 系统支付单号
     */
    private String payOrderNo;

    /**
     * 第三方交易编号（查询时传递）
     */
    private String orgPayTransactionNo;

    /**
     * 渠道方支付单号
     */
    private String channelPayOrderNo;

    /**
     * 商品名称
     **/
    private String productName;

    /**
     * 商品描述
     **/
    private String productDesc;

    /**
     * 订单描述
     */
    private String orderDesc;

    /**
     * 客户被扫时展示的付款码转换的支付编号
     */
    private String authCode;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 客户端Ip地址
     */
    private String ipAddress;

    /**
     * 支付用户编号 支付宝buyerId 微信对应openid 出款方payer
     */
    private String payerOpenid;

    /**
     * 币种，默认CNY
     */
    private String currency = "CNY";

    /**
     * 支付扩展参数
     */
    private Map<String, Object> payExtraParams;
}
