package cn.org.easysite.spring.boot.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import cn.org.easysite.commons.base.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-28 10:58
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.spring.boot.tk.mybatis.web.model.BaseQuery
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class BasePageQuery extends BaseObject {

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

}
