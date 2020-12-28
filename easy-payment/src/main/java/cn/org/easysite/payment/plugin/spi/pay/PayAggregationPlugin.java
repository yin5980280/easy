package cn.org.easysite.payment.plugin.spi.pay;

import cn.org.easysite.payment.plugin.spi.annotation.Plugin;
import cn.org.easysite.payment.plugin.spi.basic.NotifyPlugin;
import cn.org.easysite.payment.plugin.spi.basic.QueryPlugin;
import cn.org.easysite.payment.plugin.spi.model.pay.parameter.NotifyPayParameter;
import cn.org.easysite.payment.plugin.spi.model.pay.parameter.PayParameter;
import cn.org.easysite.payment.plugin.spi.model.pay.result.PayPreCreateResult;
import cn.org.easysite.payment.plugin.spi.model.pay.result.PayResult;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019/7/23 3:38 PM
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : IOT Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.pay.PayAggregationPlugin
 */
@Plugin("支付")
public interface PayAggregationPlugin extends
        PreCreatePlugin<PayParameter, PayPreCreateResult>,
        PayPlugin<PayParameter, PayResult>,
        QueryPlugin<PayParameter, PayResult>,
        NotifyPlugin<NotifyPayParameter, PayResult> {
}
