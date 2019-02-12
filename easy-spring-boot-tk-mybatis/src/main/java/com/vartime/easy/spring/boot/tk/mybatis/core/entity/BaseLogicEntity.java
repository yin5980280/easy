package com.vartime.easy.spring.boot.tk.mybatis.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vartime.easy.commons.base.BaseObject;
import com.vartime.easy.commons.json.JsonDateSerialize;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-28 11:10
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.vartime.easy.spring.boot.tk.mybatis.core.entity.BaseLogicEntity
 */
@Data
@ApiModel
@MappedSuperclass
public class BaseLogicEntity extends BaseObject {
    /**
     * 实体Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) COMMENT '主键ID,自动生成'")
    @ApiModelProperty(value = "主键编号", example = "1")
    @OrderBy(value = "DESC")
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "server_create_time", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '系统创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "系统创建时间", example = "2019-01-28 10:10:10")
    private Date serverCreateTime;

    /**
     * 更新时间
     */
    @Column(name = "server_update_time", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '系统更新时间'")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "系统更新时间", example = "2019-01-28 10:10:10")
    private Date serverUpdateTime;

    /**
     * 操作者
     */
    @Column(name = "operating", columnDefinition = "VARCHAR(32) NULL DEFAULT '' COMMENT '操作人'")
    @ApiModelProperty(value = "操作人", example = "成都熊猫")
    private String operating;

    /**
     * 是否是可用数据
     */
    @Column(name = "usable", columnDefinition = "TINYINT(1) NOT NULL DEFAULT true COMMENT '是否可用'")
    @ApiModelProperty(value = "是否可用", example = "true")
    private Boolean usable = true;
}
