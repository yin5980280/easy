package cn.org.easysite.payment.plugin.spi.pay;

import cn.org.easysite.payment.plugin.spi.basic.PaymentPlugin;
import cn.org.easysite.payment.plugin.spi.model.base.parameter.PayeePayParameter;
import cn.org.easysite.payment.plugin.spi.model.base.result.PaymentBaseResult;

/**
 * @author : 盘达天神
 * @version : 1.0
 * @date : 2020/2/14 11:17 上午
 * @Description :
 * @Copyright : Copyright (c) 2019
 * @Company : EasySite Technology 阿富汗 Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.payPayPlugin
 */
public interface PayPlugin<T extends PayeePayParameter, E extends PaymentBaseResult> extends PaymentPlugin {

    /**
     * 支付被扫/刷卡等方法调用
     *
     * @param parameter
     * @return
     */
    E pay(T parameter);

}
