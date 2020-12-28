package cn.org.easysite.payment.plugin.spi.pay.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import cn.org.easysite.framework.exception.BaseException;
import cn.org.easysite.payment.plugin.exception.PaymentException;
import cn.org.easysite.payment.plugin.spi.annotation.Channel;
import cn.org.easysite.payment.plugin.spi.annotation.PayType;
import cn.org.easysite.payment.plugin.spi.model.pay.parameter.PayParameter;
import cn.org.easysite.payment.plugin.spi.model.pay.result.PayPreCreateResult;
import cn.org.easysite.payment.plugin.spi.model.pay.result.PayResult;
import cn.org.easysite.payment.plugin.spi.pay.PayAggregationPlugin;
import lombok.extern.slf4j.Slf4j;

import static cn.org.easysite.payment.plugin.exception.PaymentErrorCode.PAYMENT_PAY_CHANNEL_PLUGIN_PAY_TYPE_NOT_FOUND_ERROR;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019/7/23 3:35 PM
 * @Description : 支付正向流程抽象
 * @Copyright : Copyright (c) 2018
 * @Company : IOT Technology ChengDu Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.pay.impl.AbstractPaymentPayPlugin
 */
@Slf4j
public abstract class AbstractPayAggregationPlugin implements PayAggregationPlugin {


    private String PAY_NOTIFY_URL_PATTERN = "pay/notify/{channelCode}/{payOrderNo}";

    /**
     * 项目启动支付渠道插件bean启动时注册到factory中
     */
    @PostConstruct
    @Override
    public void init() {
        String code = this.getChannelCode();
        //注册实现
        if (code != null && usable()) {
          //  PaymentPayPluginFactory.register(code, this);
            log.info(getChannelCode());
        }
    }

    @Override
    public PayPreCreateResult preCreate(PayParameter parameter) {
        if (!this.usable()) {
            //抛出异常
        }
        Method method = this.findMethodByPayType(parameter.getPayType());
        try {
            return (PayPreCreateResult) method.invoke(this, parameter);
        } catch (IllegalAccessException e) {
            log.error("AbstractPayAggregationPlugin#preCreate(PayParameter) 该支付方式当前插件不支持=[{}]", parameter.getPayType());
            throw new PaymentException(PAYMENT_PAY_CHANNEL_PLUGIN_PAY_TYPE_NOT_FOUND_ERROR);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof BaseException) {
                throw ((BaseException) e.getTargetException());
            }
            log.error("AbstractPayAggregationPlugin#preCreate(PayParameter) 执行出错，未捕获的异常[{}]", e.getTargetException().getMessage());
            throw new PaymentException(PAYMENT_PAY_CHANNEL_PLUGIN_PAY_TYPE_NOT_FOUND_ERROR);
        }
    }

    @Override
    public PayResult pay(PayParameter parameter) {
        if (!this.usable()) {
            //抛出异常
        }
        Method method = this.findMethodByPayType(parameter.getPayType());
        try {
            return (PayResult) method.invoke(this, parameter);
        } catch (IllegalAccessException e) {
            log.error("AbstractPayAggregationPlugin#pay(PayParameter) 该支付方式当前插件不支持=[{}]", parameter.getPayType());
            throw new PaymentException(PAYMENT_PAY_CHANNEL_PLUGIN_PAY_TYPE_NOT_FOUND_ERROR);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof BaseException) {
                throw ((BaseException) e.getTargetException());
            }
            log.error("AbstractPayAggregationPlugin#pay(PayParameter) 执行出错，未捕获的异常{}", e.getTargetException().getMessage());
            throw new PaymentException(PAYMENT_PAY_CHANNEL_PLUGIN_PAY_TYPE_NOT_FOUND_ERROR);
        }
    }

    /**
     * 反射查找有注解 @PayType("PAY_MODE_SHOWCODE") 的方法 由于annotationUtils已经给我们做了缓存，该方法不做缓存处理
     * @param payType
     * @return
     */
    private Method findMethodByPayType(String payType) {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            PayType type = AnnotationUtils.findAnnotation(method, PayType.class);
            if (type != null && type.value().equalsIgnoreCase(payType)) {
                return method;
            }
        }
        log.error("AbstractPayAggregationPlugin#findMethodByPayType(String) 该支付方式[{}]当前插件不支持", payType);
        throw new PaymentException(PAYMENT_PAY_CHANNEL_PLUGIN_PAY_TYPE_NOT_FOUND_ERROR);
    }

    /**
     * 获取支付渠道编码
     * @return 返回注册在支付渠道实现类上的channelCode也可以自定义
     */
    protected String getChannelCode() {
        Channel channel = this.getClass().getDeclaredAnnotation(Channel.class);
        if (channel == null || StringUtils.isEmpty(channel.value())) {
            log.warn("支付渠道实现配置错误，必须标注@Channel 并设置value, 未配置无法分发");
        }
        return channel == null ? null : channel.value();
    }

    /**
     * 表示该插件是否可用
     * @return
     */
    @Override
    public boolean usable() {
        return true;
    }

    @PreDestroy
    @Override
    public void destroy() {
        log.warn("当前支付渠道[{}]支付插件被卸载", getChannelCode());
    }

    /**
     * 获取异步通知地址
     * @param payParameter 支付参数
     * @return 返回组装好的支付回调地址
     */
    protected String getNotifyUrl(PayParameter payParameter) {
        return PAY_NOTIFY_URL_PATTERN.replace("{channelCode}", payParameter.getPayChannel()).replace("{payOrderNo}", payParameter.getPayOrderNo());
    }
}
