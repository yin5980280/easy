package cn.org.easysite.payment.plugin.spi.refund;

import cn.org.easysite.payment.plugin.spi.annotation.Plugin;
import cn.org.easysite.payment.plugin.spi.basic.NotifyPlugin;
import cn.org.easysite.payment.plugin.spi.basic.QueryPlugin;
import cn.org.easysite.payment.plugin.spi.model.refund.parameter.RefundNotifyPayParameter;
import cn.org.easysite.payment.plugin.spi.model.refund.parameter.RefundPayParameter;
import cn.org.easysite.payment.plugin.spi.model.refund.result.PayRefundResult;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019/7/23 3:39 PM
 * @Description : 支付退款插件集成接口
 * @Copyright : Copyright (c) 2018
 * @Company : IOT Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.refund.PayRefundAggregationPlugin
 */
@Plugin("退款")
public interface PayRefundAggregationPlugin extends
        RefundPlugin<RefundPayParameter, PayRefundResult>,
        QueryPlugin<RefundPayParameter, PayRefundResult>,
        NotifyPlugin<RefundNotifyPayParameter, PayRefundResult> {
}
