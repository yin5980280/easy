package cn.org.easysite.spring.boot.distributed.lock.model;

import cn.org.easysite.commons.base.BaseObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 锁基本信息
 * @author 潘多拉
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LockInfo extends BaseObject {

    /**
     * 锁类型
     */
    private LockType type;

    /**
     * 是否等待 默认 true 如果是true将一直等待到超时为止，否则直接返回抛出异常
     */
    private boolean tried;

    /**
     * 是否必须上锁成功才继续执行业务 默认为false即不管上锁成功与否都执行，标志为true，上锁不成功会抛出异常
     */
    private boolean required;

    /**
     * 名字
     */
    private String name;

    /**
     * 等待时间
     */
    private long waitTime;

    /**
     * 等待超时时间
     */
    private long leaseTime;

}
