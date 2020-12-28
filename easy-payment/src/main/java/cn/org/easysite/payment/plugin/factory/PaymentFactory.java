/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.payment.plugin.factory;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.org.easysite.payment.plugin.exception.PaymentException;
import cn.org.easysite.payment.plugin.spi.annotation.Channel;
import cn.org.easysite.payment.plugin.spi.annotation.Plugin;
import cn.org.easysite.payment.plugin.spi.basic.PaymentPlugin;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/12/27 2:08 下午
 * @link : cn.org.easysite.payment.plugin.factory.PaymentFactory
 */
@Slf4j
public class PaymentFactory {

    private PaymentFactory() {}

    private static final Map<Class<? extends PaymentPlugin>, Map<String, PaymentPlugin>> CONTAINER = new HashMap<>();

    public static synchronized void register(PaymentPlugin paymentPlugin) {
        if (paymentPlugin == null) {
            throw new PaymentException("插件BEAN为空无法注册");
        }
        Class<? extends PaymentPlugin> type = getPluginType(paymentPlugin);
        String channelCode = getChannelCode(paymentPlugin);
        Map<String, PaymentPlugin> plugins = CONTAINER.computeIfAbsent(type, k -> new HashMap<>());
        if (plugins.get(channelCode) != null) {
            throw new IllegalArgumentException("该渠道CODE[" + channelCode + "]实现已被[" + plugins.get(channelCode).getClass().getName() + "]注册,请检查并修改@Channel值");
        }
        log.info("正在注册[{}]类型的插件，渠道渠道编码为[{}]", type.getName(), channelCode);
        plugins.put(channelCode, paymentPlugin);
    }

    private static Class<? extends PaymentPlugin> getPluginType(PaymentPlugin paymentPlugin) {
        List<Class<?>> classes = ClassUtils.getAllInterfaces(paymentPlugin.getClass());
        if (classes == null || classes.isEmpty()) {
            throw new IllegalArgumentException("该渠道插件[" + paymentPlugin.getClass().getName() + "]实现有误");
        }
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Plugin.class)) {
                Plugin plugin = clazz.getDeclaredAnnotation(Plugin.class);
                log.info("正在注册类型为[{}]的插件", plugin.value());
                return (Class<? extends PaymentPlugin>) clazz;
            }
        }
        throw new IllegalArgumentException("该渠道插件[" + paymentPlugin.getClass().getName() + "]的聚合插件接口未标示@Plugin");
    }

    private static String getChannelCode(PaymentPlugin paymentPlugin) {
        Channel channel = paymentPlugin.getClass().getDeclaredAnnotation(Channel.class);
        if (channel == null || StringUtils.isEmpty(channel.value())) {
            log.warn("支付渠道实现配置错误，必须标注@PayChannel 并设置value, 未配置无法分发");
        }
        if (channel == null || StringUtils.isBlank(channel.value())) {
            throw new IllegalArgumentException("该渠道插件[" + paymentPlugin.getClass().getName() + "]未配置渠道值");
        }
        return channel.value();
    }

    public static PaymentPlugin getPaymentPlugin(Class<? extends PaymentPlugin> clazz, String channelCode) {
        if (clazz == null) {
            throw new PaymentException("插件类型不能为空");
        }
        if (channelCode == null) {
            throw new PaymentException("渠道编码不能为空");
        }
        Map<String, PaymentPlugin> plugins = CONTAINER.get(clazz);
        if (plugins == null) {
            throw new PaymentException("该类型插件[" + clazz.getName() + "]尚未支持");
        }
        PaymentPlugin paymentPlugin = plugins.get(channelCode);
        if (paymentPlugin == null) {
            throw new PaymentException("该类型插件[" + clazz.getName() + "]下该渠道[" + channelCode + "]尚未支持");
        }
        return paymentPlugin;
    }
}
