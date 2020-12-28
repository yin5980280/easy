package cn.org.easysite.payment.plugin.spi.model.base.result;

import cn.org.easysite.commons.base.BaseObject;
import lombok.Data;

/**
 * @author : 盘达天神
 * @version : 1.0
 * @date : 2020/2/14 10:19 上午
 * @Description :
 * @Copyright : Copyright (c) 2019
 * @Company : EasySite Technology 阿富汗 Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.model.base.result.PaymentBaseResult
 */
@Data
public class PaymentBaseResult extends BaseObject {

    /**
     * 状态描述啥的
     */
    private String message;

    /**
     * 请求是否成功
     */
    private Boolean success;

    protected String code;

    /**
     * 回调通知返回CODE
     */
    private String notifyReturnCode = "SUCCESS";
}
