package cn.org.easysite.payment.plugin.spi.basic;

import cn.org.easysite.payment.plugin.spi.model.base.parameter.PayeePayParameter;
import cn.org.easysite.payment.plugin.spi.model.base.result.PaymentBaseResult;

/**
 * 回调通知基础插件类
 * @author yinlin
 */
public interface NotifyPlugin<T extends PayeePayParameter, E extends PaymentBaseResult> extends PaymentPlugin {

    /**
     * 回调抽象接口
     * @param notify 回调参数实体
     * @return 返回回调解析结果
     */
    E notify(T notify);
}
