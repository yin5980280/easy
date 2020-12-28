/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.payment.plugin.spi.model.base.parameter;

import java.util.Map;

import cn.org.easysite.commons.base.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/12/27 12:02 下午
 * @link : cn.org.easysite.payment.plugin.spi.model.base.parameter.BasePayParameter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePayParameter extends BaseObject {
    /**
     * 支付渠道编码
     */
    private String payChannel;

    /**
     * 支付机构编码
     */
    private String payOrg;

    /**
     * 支付类型编码
     */
    private String payType;

    /**
     * 支付参数扩展参数
     */
    private Map<String, Object> basePayExtraParams;
}
