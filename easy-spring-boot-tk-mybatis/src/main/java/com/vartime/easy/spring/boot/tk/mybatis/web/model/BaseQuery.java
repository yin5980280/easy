package com.vartime.easy.spring.boot.tk.mybatis.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.vartime.easy.commons.base.BaseObject;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-28 10:58
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.vartime.easy.spring.boot.tk.mybatis.web.model.BaseQuery
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class BaseQuery extends BaseObject {

    @ApiModelProperty(value = "页码", dataType = "Integer")
    private Integer page;

    @ApiModelProperty(value = "每页行数", dataType = "Integer")
    private Integer rows;

    @ApiModelProperty(value = "创建结束日期 yyyy-MM-dd", dataType = "String")
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty(value = "创建开始日期 yyyy-MM-dd", dataType = "String")
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 初始化查询配置
     */
    public void pageStart() {
        PageHelper.startPage(page == null || page <= 0 ? 1 : page, rows == null || rows <= 0 || rows > 1000 ? 15 : rows);
    }

    public Date getEndDate() {
        if (this.endDate != null) {
            return DateUtils.addDays(endDate, INTEGER_ONE);
        }
        return null;
    }

}
