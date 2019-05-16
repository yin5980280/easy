package cn.org.easysite.spring.boot.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

import cn.org.easysite.spring.boot.jpa.entity.BaseEntity;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-04-30 10:06
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.spring.boot.jpa.repository.LogicJpaRepository
 */
@NoRepositoryBean
public interface LogicJpaRepository<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {

}
