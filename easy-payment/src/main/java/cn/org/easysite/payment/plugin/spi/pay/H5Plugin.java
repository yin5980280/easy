/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.payment.plugin.spi.pay;

import cn.org.easysite.payment.plugin.spi.annotation.PayType;
import cn.org.easysite.payment.plugin.spi.model.pay.parameter.PayParameter;
import cn.org.easysite.payment.plugin.spi.model.pay.result.PayPreCreateResult;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/12/28 5:32 下午
 * @link : com.iot.trade.core.payment.gateway.plugin.spi.pay.H5Plugin
 */
public interface H5Plugin extends PreCreatePlugin<PayParameter, PayPreCreateResult> {
    /**
     * H5支付
     * @param payParameter
     * @return
     */
    @PayType("H5")
    PayPreCreateResult h5(PayParameter payParameter);
}
