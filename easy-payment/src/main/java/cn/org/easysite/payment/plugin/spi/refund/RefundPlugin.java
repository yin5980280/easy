package cn.org.easysite.payment.plugin.spi.refund;

import cn.org.easysite.payment.plugin.spi.basic.PaymentPlugin;
import cn.org.easysite.payment.plugin.spi.model.base.parameter.PayeePayParameter;
import cn.org.easysite.payment.plugin.spi.model.base.result.PaymentBaseResult;

/**
 * 退款基础插件
 * @author yinlin
 */
public interface RefundPlugin<T extends PayeePayParameter, E extends PaymentBaseResult> extends PaymentPlugin {

    /**
     * 支付退款方法
     * @param parameter 支付退款参数
     * @return 支付退款返回值
     */
    E refund(T parameter);
}
