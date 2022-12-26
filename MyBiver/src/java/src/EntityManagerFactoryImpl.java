package src;

import src.entity.EntityBean;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import java.util.Map;

public class EntityManagerFactoryImpl implements EntityManagerFactory {
    public EntityManagerFactoryImpl(DataSourcePool dataSource, Map<Class<?>, EntityBean> entityBeanMap) {
        this.dataSource = dataSource;
        this.entityBeanMap = entityBeanMap;
    }

    DataSourcePool dataSource;
    Map<Class<?>, EntityBean> entityBeanMap;

    @Override
    public EntityManager createEntityManager() {
        return new EntityManagerImpl(entityBeanMap, dataSource);
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        return null;
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        return null;
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        return null;
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return null;
    }

    @Override
    public Metamodel getMetamodel() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public Map<String, Object> getProperties() {
        return null;
    }

    @Override
    public Cache getCache() {
        return null;
    }

    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return null;
    }

    @Override
    public void addNamedQuery(String s, Query query) {

    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> void addNamedEntityGraph(String s, EntityGraph<T> entityGraph) {

    }
}
