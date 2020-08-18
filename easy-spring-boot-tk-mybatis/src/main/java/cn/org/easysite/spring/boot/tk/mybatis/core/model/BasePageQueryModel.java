/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.spring.boot.tk.mybatis.core.model;

import com.github.pagehelper.PageHelper;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

import cn.org.easysite.commons.base.BaseObject;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/8/18 2:41 下午
 * @link : cn.org.easysite.spring.boot.tk.mybatis.core.model.BaseQueryModel
 */
public class BasePageQueryModel extends BaseObject {

    private Integer page;

    private Integer rows;

    private Date endDate;

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
