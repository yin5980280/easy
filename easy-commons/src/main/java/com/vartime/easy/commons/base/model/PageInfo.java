package com.vartime.easy.commons.base.model;

import com.github.pagehelper.Page;
import com.vartime.easy.commons.base.BaseObject;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-02-12 09:32
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.runshang.apps.cloudgame.commons.base.model.PageInfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo<T> extends BaseObject {

    /**
     * 当前页号（前端传入）
     */
    private Integer pageNum;

    /**
     * 每页像是条数（前端传入）
     */
    private Integer pageSize;

    /**
     * 当前查询条件下：一共多少条数据
     */
    private Long total;

    /**
     * 当前页的数据
     */
    private List<T> list;

    public static PageInfo valueOf(Page page) {
        return new PageInfo(page.getPageNum(), page.getPageSize(), page.getTotal(), page);
    }
}
