package cn.org.easysite.payment.plugin.spi.basic;

/**
 * 支付插件基础类
 * @author yinlin
 */
public interface PaymentPlugin {

    /**
     * 插件初始化
     */
    void init();

    /**
     * 当前插件是否可用，留给接口或者插件实现类自己实现
     * @return true 可用 false 不可用
     */
    boolean usable();

    /**
     * 插件销毁
     */
    void destroy();
}
