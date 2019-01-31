package com.vartime.easy.framework.utils;

import org.springframework.context.MessageSource;

/**
 * 获取i18n资源文件
 *
 * @author panda
 */
public class MessageUtils {
    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return
     */
    public static String message(String code, Object... args) {
        MessageSource messageSource = SpringApplicationUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, null);
    }
}
