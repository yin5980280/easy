package com.vartime.easy.spring.boot.tk.mybatis.core.service.impl;


import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vartime.easy.spring.boot.tk.mybatis.core.mapper.CommonMapper;
import com.vartime.easy.spring.boot.tk.mybatis.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import tk.mybatis.mapper.entity.Example;

/**
 * BaseService 实现类
 *
 * @author Panda
 */
public abstract class AbstractBaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private CommonMapper<T> mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(T record) {
        Preconditions.checkNotNull(record);
        return mapper.insertSelective(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUnchecked(T record) {
        Preconditions.checkNotNull(record);
        return mapper.insert(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(List<T> records) {
        return mapper.insertList(records);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(T record) {
        Preconditions.checkNotNull(record);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUnchecked(T record) {
        Preconditions.checkNotNull(record);
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByExample(T record, Example example) {
        Preconditions.checkNotNull(record);
        Preconditions.checkNotNull(example);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUncheckedByExample(T record, Example example) {
        Preconditions.checkNotNull(record);
        Preconditions.checkNotNull(example);
        return mapper.updateByExample(record, example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPk(Serializable pk) {
        Preconditions.checkNotNull(pk);
        return mapper.deleteByPrimaryKey(pk);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPks(Iterable<? extends Serializable> pks) {
        Preconditions.checkNotNull(pks);
        String pksStr = Joiner.on(',').skipNulls().join(pks);
        return mapper.deleteByIds(pksStr);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(T param) {
        Preconditions.checkNotNull(param);
        return mapper.delete(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll() {
        return mapper.delete(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByExample(Example example) {
        Preconditions.checkNotNull(example);
        return mapper.deleteByExample(example);
    }

    @Override
    public T selectByPk(Serializable pk) {
        Preconditions.checkNotNull(pk);
        return mapper.selectByPrimaryKey(pk);
    }

    @Override
    public List<T> selectByPks(Iterable<? extends Serializable> pks) {
        Preconditions.checkNotNull(pks);
        String pksStr = Joiner.on(',').skipNulls().join(pks);
        return mapper.selectByIds(pksStr);
    }

    @Override
    public List<T> select(T param) {
        Preconditions.checkNotNull(param);
        return mapper.select(param);
    }

    @Override
    public T selectOne(T param) {
        Preconditions.checkNotNull(param);
        PageHelper.offsetPage(0, 1, false);
        return mapper.selectOne(param);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public int selectCount(T param) {
        Preconditions.checkNotNull(param);
        return mapper.selectCount(param);
    }

    @Override
    public PageInfo<T> selectPage(T param, int pageNum, int pageSize) {
        Preconditions.checkNotNull(param);
        return PageHelper.startPage(pageNum, pageSize, false).doSelectPageInfo(() -> mapper.select(param));
    }

    @Override
    public PageInfo<T> selectPageAndCount(T param, int pageNum, int pageSize) {
        Preconditions.checkNotNull(param);
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> mapper.select(param));
    }

    @Override
    public List<T> selectByExample(Example example) {
        Preconditions.checkNotNull(example);
        return mapper.selectByExample(example);
    }

    @Override
    public T selectOneByExample(Example example) {
        Preconditions.checkNotNull(example);
        return mapper.selectOneByExample(example);
    }

    @Override
    public int selectCountByExample(Example example) {
        Preconditions.checkNotNull(example);
        return mapper.selectCountByExample(example);
    }

    @Override
    public PageInfo<T> selectPageByExample(Example example, int pageNum, int pageSize) {
        Preconditions.checkNotNull(example);
        return PageHelper.startPage(pageNum, pageSize, false)
                .doSelectPageInfo(() -> mapper.selectByExample(example));
    }

    @Override
    public PageInfo<T> selectPageAndCountByExample(Example example, int pageNum, int pageSize) {
        Preconditions.checkNotNull(example);
        return PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> mapper.selectByExample(example));
    }

}
