package org.springframework.data.jpa.repository.support;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cn.org.easysite.spring.boot.jpa.entity.BaseEntity;
import cn.org.easysite.spring.boot.jpa.repository.LogicJpaRepository;

import static org.springframework.data.jpa.repository.query.QueryUtils.applyAndBind;
import static org.springframework.data.jpa.repository.query.QueryUtils.getQueryString;
import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-04-30 10:10
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.spring.boot.jpa.repository.LogicJpaRepositoryImpl
 */
public class LogicJpaRepositoryImpl<T extends BaseEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements LogicJpaRepository<T, ID> {

    /**
     * 逻辑删除字段名.
     */
    public final static String USABLE_FIELD = "usable";


    public static final String COUNT_QUERY_STRING = "select count(%s) from %s x where x.usable = true";

    public static final String EXISTS_QUERY_STRING = "select count(%s) from %s x where x.%s = :id and x.usable = true";

    public static final String DELETE_ALL_QUERY_STRING = "update %s x set s.usable = false";

    private final JpaEntityInformation<T, ?> entityInformation;

    private final EntityManager em;

    private final PersistenceProvider provider;

    private @Nullable CrudMethodMetadata metadata;

    public LogicJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
        this.entityInformation = entityInformation;
        this.provider = PersistenceProvider.fromEntityManager(entityManager);
    }

    /**
     * Creates a {@link TypedQuery} for the given {@link Specification} and {@link Sort}.
     *
     * @param spec can be {@literal null}.
     * @param domainClass must not be {@literal null}.
     * @param sort must not be {@literal null}.
     * @return
     */
    @Override
    public <S extends T> TypedQuery<S> getQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<S> query = builder.createQuery(domainClass);

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);
        query.select(root);

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }
        return applyRepositoryMethodMetadata(em.createQuery(query));
    }

    /**
     * Creates a new count query for the given {@link Specification}.
     *
     * @param spec can be {@literal null}.
     * @param domainClass must not be {@literal null}.
     * @return
     */
    @Override
    protected <S extends T> TypedQuery<Long> getCountQuery(@Nullable Specification<S> spec, Class<S> domainClass) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }

        // Remove all Orders the Specifications might have applied
        query.orderBy(Collections.<Order> emptyList());

        return em.createQuery(query);
    }

    protected <S> TypedQuery<S> applyRepositoryMethodMetadata(TypedQuery<S> query) {

        if (metadata == null) {
            return query;
        }

        LockModeType type = metadata.getLockModeType();
        TypedQuery<S> toReturn = type == null ? query : query.setLockMode(type);

        applyQueryHints(toReturn);
        return toReturn;
    }

    private void applyQueryHints(Query query) {

        for (Map.Entry<String, Object> hint : getQueryHints().withFetchGraphs(em)) {
            query.setHint(hint.getKey(), hint.getValue());
        }
    }

    /**
     * Applies the given {@link Specification} to the given {@link CriteriaQuery}.
     *
     * @param spec can be {@literal null}.
     * @param domainClass must not be {@literal null}.
     * @param query must not be {@literal null}.
     * @return
     */
    protected <S, U extends T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass,
            CriteriaQuery<S> query) {

        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");

        Root<U> root = query.from(domainClass);
        CriteriaBuilder builder = em.getCriteriaBuilder();
        Path<Boolean> deletedPath = root.get(USABLE_FIELD);
        Predicate deletedPredicate = builder.isTrue(deletedPath);
        if (spec == null) {
            query.where(deletedPath);
            return root;
        }

        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            predicate = builder.and(deletedPredicate);
            query.where(predicate);
        }

        return root;
    }

    @Override
    public long count() {
        return em.createQuery(getCountQueryString(), Long.class).getSingleResult();
    }

    private String getCountQueryString() {
        String countQuery = String.format(COUNT_QUERY_STRING, provider.getCountQueryPlaceholder(), "%s");
        return getQueryString(countQuery, entityInformation.getEntityName());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(T entity) {
        Assert.notNull(entity, "The entity must not be null!");
        entity.setUsable(false);
        em.persist(entity);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaRepository#deleteInBatch(java.lang.Iterable)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteInBatch(Iterable<T> entities) {

        Assert.notNull(entities, "The given Iterable of entities not be null!");

        if (!entities.iterator().hasNext()) {
            return;
        }

        applyAndBind(getQueryString(DELETE_ALL_QUERY_STRING, entityInformation.getEntityName()), entities, em)
                .executeUpdate();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaRepository#deleteAllInBatch()
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAllInBatch() {
        em.createQuery(getDeleteAllQueryString()).executeUpdate();
    }

    protected String getDeleteAllQueryString() {
        return getQueryString(DELETE_ALL_QUERY_STRING, entityInformation.getEntityName());
    }

    @Override
    public T getOne(ID id) {
        T t = super.getOne(id);
        return t.getUsable() ? t : null;
    }
}
