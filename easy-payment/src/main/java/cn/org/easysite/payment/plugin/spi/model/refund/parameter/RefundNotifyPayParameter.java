package cn.org.easysite.payment.plugin.spi.model.refund.parameter;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 盘达天神
 * @version : 1.0
 * @date : 2020/2/14 9:32 上午
 * @Description :
 * @Copyright : Copyright (c) 2019
 * @Company : EasySite Technology 阿富汗 Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.model.refund.parameter.RefundNotifyPayParameter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundNotifyPayParameter extends RefundPayParameter {
    /**
     * 通知回调HttpServletRequest.getRequestParam 所有回调参数map json
     */
    private Map<String, String> notifyRequestMap;

    /**
     * request headers
     */
    private Map<String, String> requestHeaderMap;

    /**
     * 通知回调请求body的字符串，有的回调可能是流也需要转成String
     */
    private String notifyRequestBody;
}
