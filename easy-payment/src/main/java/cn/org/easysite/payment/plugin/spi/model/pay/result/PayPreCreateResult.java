package cn.org.easysite.payment.plugin.spi.model.pay.result;

import java.util.Map;

import cn.org.easysite.payment.plugin.spi.model.base.result.PaymentBaseResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019/7/23 4:36 PM
 * @Description : 预下单/一码付返回
 * @Copyright : Copyright (c) 2018
 * @Company : IOT Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.model.pay.result.PaymentPrepayResultPayment
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PayPreCreateResult extends PaymentBaseResult {

    private Boolean form;

    private Boolean qrcode;

    /**
     * 支付渠道方订单号
     */
    private String channelPayOrderNo;

    /**
     * 参数
     */
    private Map<String, String> prepayParam;

    /**
     * 原生二维码CODE字符串
     */
    private String qrcodeUrl;

    /**
     * FORM
     */
    private String formBody;
}
