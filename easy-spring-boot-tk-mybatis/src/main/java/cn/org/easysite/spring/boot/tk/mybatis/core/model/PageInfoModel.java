package cn.org.easysite.spring.boot.tk.mybatis.core.model;

import com.github.pagehelper.Page;

import java.util.List;

import cn.org.easysite.commons.base.BaseObject;
import cn.org.easysite.commons.base.bean.BeanConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-02-12 09:32
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : com.runshang.apps.cloudgame.commons.base.model.PageInfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoModel<T> extends BaseObject {

    /**
     * 当前页号（前端传入）
     */
    private Integer page;

    /**
     * 每页像是条数（前端传入）
     */
    private Integer rows;

    /**
     * 当前查询条件下：一共多少条数据
     */
    private Long total;

    /**
     * 当前页的数据
     */
    private List<T> list;

    public static PageInfoModel valueOf(Page page) {
        return new PageInfoModel(page.getPageNum(), page.getPageSize(), page.getTotal(), page);
    }

    public static PageInfoModel valueOf(List list) {
        if (list instanceof Page) {
            return valueOf((Page) list);
        } else {
            return new PageInfoModel(1, list.size(), Long.valueOf(list.size()), list);
        }
    }

    public static PageInfoModel valueOf(List list, Class<?> clazz) {
        if (list instanceof Page) {
            return valueOf((Page)list, clazz);
        } else {
            return new PageInfoModel(1, list.size(), Long.valueOf(list.size()), BeanConverter.convert(clazz, list));
        }
    }

    public static PageInfoModel valueOf(Page page, Class<?> clazz) {
        PageInfoModel pageInfo = valueOf(page);
        pageInfo.list = BeanConverter.convert(clazz, page);
        return pageInfo;
    }
}
