package com.vartime.easy.spring.boot.tk.mybatis.core.service;


import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

import tk.mybatis.mapper.entity.Example;

/**
 * BaseService，包含常用的单表操作
 *
 * @author trang
 */
public interface BaseService<T> {

    // ------ C ------ //

    /**
     * 新增数据，值为 null 的字段不会保存（可以保留字段的默认值）
     *
     * @param record 待保存的数据
     * @return int 影响行数
     */
    int insert(T record);

    /**
     * 新增数据，值为 null 的字段也会保存
     *
     * @param record 待保存的数据
     * @return int 影响行数
     */
    int insertUnchecked(T record);

    /**
     * 批量新增数据，值为 null 的字段也会保存
     *
     * @param records 待保存的数据
     * @return int 影响行数
     */
    int insertBatch(List<T> records);

    // ------ U ------ //

    /**
     * 根据主键更新数据，值为 null 的字段不会更新
     *
     * @param record 待更新的数据
     * @return int 影响行数
     */
    int update(T record);

    /**
     * 根据主键更新数据，值为 null 的字段会更新为 null
     *
     * @param record 待更新的数据
     * @return int 影响行数
     */
    int updateUnchecked(T record);

    /**
     * 根据 Example 条件更新数据，值为 null 的字段不会更新
     *
     * @param record  待更新的数据
     * @param example where 条件
     * @return int 影响行数
     */
    int updateByExample(T record, Example example);

    /**
     * 根据 Example 条件更新数据，值为 null 的字段会更新为 null
     *
     * @param record  待更新的数据
     * @param example where 条件
     * @return int 影响行数
     */
    int updateUncheckedByExample(T record, Example example);

    // ------ D ------ //

    /**
     * 根据主键删除数据
     *
     * @param pk 主键
     * @return int 影响行数
     */
    int deleteByPk(Serializable pk);

    /**
     * 根据主键集合删除数据
     *
     * @param pks 主键集合
     * @return int 影响行数
     */
    int deleteByPks(Iterable<? extends Serializable> pks);

    /**
     * 根据 `=` 条件删除数据
     *
     * @param param where 条件
     * @return int 影响行数
     */
    int delete(T param);

    /**
     * 删除全部数据
     *
     * @return int 影响行数
     */
    int deleteAll();

    /**
     * 根据 Example 条件删除数据
     *
     * @param example where 条件
     * @return int 影响行数
     */
    int deleteByExample(Example example);

    // ------ R ------ //

    /**
     * 根据主键查询数据
     *
     * @param pk 主键
     * @return T 实体
     */
    T selectByPk(Serializable pk);

    /**
     * 根据主键集合查询数据
     *
     * @param pks 主键集合
     * @return List<T> 实体集合
     */
    List<T> selectByPks(Iterable<? extends Serializable> pks);

    /**
     * 根据 `=` 条件查询数据集合
     *
     * @param param where 条件
     * @return List<T> 实体集合
     */
    List<T> select(T param);

    /**
     * 根据 `=` 条件查询单条数据
     *
     * @param param where 条件
     * @return T 实体
     */
    T selectOne(T param);

    /**
     * 查询全部数据
     *
     * @return List<T> 实体集合
     */
    List<T> selectAll();


    /**
     * 根据 `=` 条件查询数据数量
     *
     * @param param where 条件
     * @return int 数据量
     */
    int selectCount(T param);


    /**
     * 根据 `=` 条件分页查询，不会查询 count
     *
     * @param param    where 条件
     * @param pageNum  分页页码
     * @param pageSize 分页数量
     * @return PageInfo<T> 分页实体
     */
    PageInfo<T> selectPage(T param, int pageNum, int pageSize);

    /**
     * 根据 `=` 条件分页查询，查询count
     * 若同时需要排序，可手动指定PageHelper.orderBy()
     *
     * @param param    where 条件
     * @param pageNum  分页页码
     * @param pageSize 分页数量
     * @return PageInfo<T> 分页实体
     */
    PageInfo<T> selectPageAndCount(T param, int pageNum, int pageSize);

    /**
     * 根据 Example 条件查询数据集合
     *
     * @param example where 条件
     * @return List<T> 实体集合
     */
    List<T> selectByExample(Example example);

    /**
     * 根据条件查询一个
     * @param example
     * @return
     */
    T selectOneByExample(Example example);

    /**
     * 根据 Example 条件查询数据数量
     *
     * @param example where 条件
     * @return int 数据量
     */
    int selectCountByExample(Example example);

    /**
     * 根据 Example 条件分页查询，不会查询 count
     *
     * @param example  where 条件
     * @param pageNum  分页页码
     * @param pageSize 分页数量
     * @return PageInfo<T> 分页实体
     */
    PageInfo<T> selectPageByExample(Example example, int pageNum, int pageSize);

    /**
     * 根据 Example 条件分页查询，不会查询 count
     *
     * @param example  where 条件
     * @param pageNum  分页页码
     * @param pageSize 分页数量
     * @return PageInfo<T> 分页实体
     */
    PageInfo<T> selectPageAndCountByExample(Example example, int pageNum, int pageSize);

}
