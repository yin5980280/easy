package cn.org.easysite.spring.boot.jpa.factory.bean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.jpa.repository.support.LogicJpaRepositoryImpl;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

import javax.persistence.EntityManager;

import cn.org.easysite.spring.boot.jpa.entity.BaseEntity;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-05-16 17:44
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.spring.boot.jpa.factory.bean.LogicJpaRespositoryFactoryBean
 */
public class LogicJpaRepositoryFactoryBean<R extends JpaRepository<T, ID>, T extends BaseEntity, ID extends Serializable> extends JpaRepositoryFactoryBean<R, T, ID> {
    /**
     * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public LogicJpaRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new LogicRepositoryFactory(entityManager);
    }

    public static class LogicRepositoryFactory<T extends BaseEntity, ID extends Serializable> extends JpaRepositoryFactory {

        private final EntityManager em;
        /**
         * Creates a new {@link JpaRepositoryFactory}.
         *
         * @param entityManager must not be {@literal null}
         */
        public LogicRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.em = entityManager;
        }

        @Override
        protected JpaRepositoryImplementation getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            return new LogicJpaRepositoryImpl<T, ID>(JpaEntityInformationSupport.getEntityInformation((Class<T>) information.getDomainType(), em), entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return LogicJpaRepositoryImpl.class;
        }
    }
}
