/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.payment.plugin.spi.refund.impl;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import cn.org.easysite.payment.plugin.spi.annotation.Channel;
import cn.org.easysite.payment.plugin.spi.refund.PayRefundAggregationPlugin;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/12/27 4:17 下午
 * @link : cn.org.easysite.payment.plugin.spi.refund.impl
 * .AbstractPayRefundAggregationPlugin
 */
@Slf4j
public abstract class AbstractPayRefundAggregationPlugin implements PayRefundAggregationPlugin {
    /**
     * 项目启动支付渠道插件bean启动时注册到factory中
     */
    @PostConstruct
    @Override
    public void init() {

    }

    @Override
    public boolean usable() {
        return true;
    }

    @PreDestroy
    @Override
    public void destroy() {
        log.info("当前支付渠道[{}]退款插件被卸载", getChannelCode());
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
     * 组装退款url
     * @param outTradeNo
     * @return
     */
    protected String getNotifyUrl(String outTradeNo) {
        return "";
    }
}
