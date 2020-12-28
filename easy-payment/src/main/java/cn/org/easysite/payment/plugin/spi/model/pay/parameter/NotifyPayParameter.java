/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.payment.plugin.spi.model.pay.parameter;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/12/27 10:53 上午
 * @link : cn.org.easysite.payment.plugin.spi.model.pay.parameter.PaymentPayNotifyParameter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyPayParameter extends PayParameter {

    /**
     * 通知回调HttpServletRequest.getHeaderMap 所有回调参数map
     */
    private Map<String, String> notifyRequestHeaderMap;

    /**
     * 通知回调HttpServletRequest.getRequestParam 所有回调参数map
     */
    private Map<String, String> notifyRequestMap;

    /**
     * 通知回调请求body的字符串，有的回调可能是流也需要转成String
     */
    private String notifyRequestBody;

    /**
     * 退款扩展参数
     */
    private Map<String, Object> refundExtraParams;
}
