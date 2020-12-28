package cn.org.easysite.payment.plugin.spi.basic;

import cn.org.easysite.payment.plugin.spi.model.base.parameter.PayeePayParameter;
import cn.org.easysite.payment.plugin.spi.model.base.result.PaymentBaseResult;

/**
 * 支付查询基础类
 * @author yinlin
 */
public interface QueryPlugin<T extends PayeePayParameter, E extends PaymentBaseResult> extends PaymentPlugin {

    /**
     * 查询第三方订单状态
     * @param parameter 支付参数
     * @return 返回三方订单详情
     */
    E query(T parameter);
}
