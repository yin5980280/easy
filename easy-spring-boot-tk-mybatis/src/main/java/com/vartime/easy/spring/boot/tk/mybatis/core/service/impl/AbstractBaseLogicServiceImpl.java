package com.vartime.easy.spring.boot.tk.mybatis.core.service.impl;

import com.github.pagehelper.PageInfo;
import com.vartime.easy.spring.boot.tk.mybatis.core.entity.BaseLogicEntity;
import com.vartime.easy.spring.boot.tk.mybatis.core.mapper.CommonMapper;
import com.vartime.easy.spring.boot.tk.mybatis.core.service.BaseLogicService;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-29 10:17
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.vartime.easy.spring.boot.tk.mybatis.core.service.impl.AbstractBaseLogicService
 */
public abstract class AbstractBaseLogicServiceImpl<T extends BaseLogicEntity> extends AbstractBaseServiceImpl<T> implements BaseLogicService<T> {

    @Autowired
    private CommonMapper<T> commonMapper;

    @Override
    public List<T> select(T param) {
        param.setUsable(true);
        return super.select(param);
    }

    @Override
    public int delete(T param) {
        param.setUsable(false);
        return super.update(param);
    }

    @Override
    public T selectByPk(Serializable pk) {
        T entity = super.selectByPk(pk);
        return entity != null && entity.getUsable() ? entity : null;
    }

    @Override
    public List<T> selectAll() {
        BaseLogicEntity param = new BaseLogicEntity();
        param.setUsable(true);
        return super.select((T) param);
    }

    @Override
    public List<T> selectByPks(Iterable<? extends Serializable> pks) {
        List<T> entities = super.selectByPks(pks);
        for (int i = entities.size() - 1; i >= 0; i --) {
            if (!entities.get(i).getUsable()) {
                entities.remove(i);
            }
        }
        return entities;
    }

    @Override
    public T selectOne(T param) {
        param.setUsable(true);
        return super.selectOne(param);
    }

    @Override
    public PageInfo<T> selectPage(T param, int pageNum, int pageSize) {
        param.setUsable(true);
        return super.selectPage(param, pageNum, pageSize);
    }

    @Override
    public PageInfo<T> selectPageAndCount(T param, int pageNum,
            int pageSize) {
        param.setUsable(true);
        return super.selectPageAndCount(param, pageNum, pageSize);
    }

}
