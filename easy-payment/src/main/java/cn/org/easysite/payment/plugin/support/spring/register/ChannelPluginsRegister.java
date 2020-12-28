/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.payment.plugin.support.spring.register;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

import cn.org.easysite.payment.plugin.factory.PaymentFactory;
import cn.org.easysite.payment.plugin.spi.basic.PaymentPlugin;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/12/27 3:59 下午
 * @link : cn.org.easysite.payment.plugin.support.spring.register.ChannelPluginsRegister
 */
@Slf4j
public class ChannelPluginsRegister implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, PaymentPlugin> map = applicationContext.getBeansOfType(PaymentPlugin.class);
        if (map.isEmpty()) {
            log.error("PaymentPlugin Spring Bean not found");
            return;
        }
        map.forEach((k, v) -> PaymentFactory.register(v));
    }
}
