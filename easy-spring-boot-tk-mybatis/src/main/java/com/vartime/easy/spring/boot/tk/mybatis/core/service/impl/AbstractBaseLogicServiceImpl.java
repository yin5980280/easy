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
public abstract class AbstractBaseLogicServiceImpl extends AbstractBaseServiceImpl<BaseLogicEntity> implements BaseLogicService {

    @Autowired
    private CommonMapper<BaseLogicEntity> commonMapper;

    @Override
    public List<BaseLogicEntity> select(BaseLogicEntity param) {
        param.setUsable(true);
        return super.select(param);
    }

    @Override
    public int delete(BaseLogicEntity param) {
        param.setUsable(false);
        return super.update(param);
    }

    @Override
    public BaseLogicEntity selectByPk(Serializable pk) {
        BaseLogicEntity entity = super.selectByPk(pk);
        return entity != null && entity.getUsable() ? entity : null;
    }

    @Override
    public List<BaseLogicEntity> selectAll() {
        BaseLogicEntity param = new BaseLogicEntity();
        param.setUsable(true);
        return super.select(param);
    }

    @Override
    public List<BaseLogicEntity> selectByPks(Iterable<? extends Serializable> pks) {
        List<BaseLogicEntity> entities = super.selectByPks(pks);
        for (int i = entities.size() - 1; i >= 0; i --) {
            if (!entities.get(i).getUsable()) {
                entities.remove(i);
            }
        }
        return entities;
    }

    @Override
    public BaseLogicEntity selectOne(BaseLogicEntity param) {
        param.setUsable(true);
        return super.selectOne(param);
    }

    @Override
    public PageInfo<BaseLogicEntity> selectPage(BaseLogicEntity param, int pageNum, int pageSize) {
        param.setUsable(true);
        return super.selectPage(param, pageNum, pageSize);
    }

    @Override
    public PageInfo<BaseLogicEntity> selectPageAndCount(BaseLogicEntity param, int pageNum,
            int pageSize) {
        param.setUsable(true);
        return super.selectPageAndCount(param, pageNum, pageSize);
    }

}
