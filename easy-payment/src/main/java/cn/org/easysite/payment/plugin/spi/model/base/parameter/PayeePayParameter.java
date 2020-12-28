package cn.org.easysite.payment.plugin.spi.model.base.parameter;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 盘达天神
 * @version : 1.0
 * @date : 2020/2/14 9:22 上午
 * @Description :
 * @Copyright : Copyright (c) 2019
 * @Company : EasySite Technology 阿富汗 Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.model.PaymentPayeeParameter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayeePayParameter extends BasePayParameter {

    /**
     * 支付渠道商户支付参数JSON字符串,需要使用方自己解析
     */
    private Map<String, String> payeeParams;

    /**
     * 支付参数扩展参数
     */
    private Map<String, Object> payeeExtraParams;

}
