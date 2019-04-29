package cn.org.easysite.spring.boot.jpa.entiy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.org.easysite.commons.base.BaseObject;
import lombok.Data;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2018/9/26 下午6:01
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.mycloud.framework.entity.BaseEntity
 */
@Data
@MappedSuperclass
public class BaseEntity extends BaseObject {

    /**
     * 实体Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) COMMENT '主键ID,自动生成'")
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "server_create_time", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '系统创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serverCreateTime;

    /**
     * 更新时间
     */
    @Column(name = "server_update_time", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '系统更新时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serverUpdateTime;

    /**
     * 操作者
     */
    @Column(name = "operating", columnDefinition = "VARCHAR(32) NULL DEFAULT '' COMMENT '操作人'")
    private String operating;

    /**
     * 是否删除
     */
    @Column(name = "usable", columnDefinition = "TINYINT(1) NOT NULL DEFAULT true COMMENT '是否可用'")
    private Boolean usable = true;
}
