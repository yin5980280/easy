package cn.org.easysite.payment.plugin.spi.model.pay.result;

import cn.org.easysite.payment.plugin.spi.model.base.result.PaymentBaseResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019/7/23 4:41 PM
 * @Description : 用户被扫/查询/notify返回支付结果
 * @Copyright : Copyright (c) 2018
 * @Company : IOT Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.model.pay.result.PaymentThirdCouponResultPayment
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayThirdCouponResult extends PaymentBaseResult {

    /**
     * 优惠券编码
     */
    private String code;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券类型
     */
    private String type;
    /**
     *    金额 单位（分）
     */
    private Long amount;
}
