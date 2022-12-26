package src;


import src.entity.EntityBean;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.*;

public class EntityManagerImpl implements EntityManager {

    DataSourcePool dataSource;
    Map<Class<?>, EntityBean> entityBeanMap;


    public EntityManagerImpl(Map<Class<?>, EntityBean> entityBeanMap, DataSourcePool dataSourcePool) {
        this.entityBeanMap = entityBeanMap;
        this.dataSource = dataSourcePool;
    }


    public List<Object> findAll(Class<?> aClass)  {
        try {
            Connection connection = dataSource.getConnection();
            EntityBean bean = entityBeanMap.get(aClass);
            Map<Long, Object> result = new HashMap<>();
            ResultSet primitiveResultSet = connection.createStatement().executeQuery(bean.getQueryBean().getSimpleSelectQuery());
            while (primitiveResultSet.next()) {
                Object object = aClass.newInstance();

                Field idField = aClass.getDeclaredField(bean.getIdName());
                idField.setAccessible(true);
                Long id = primitiveResultSet.getLong(bean.getIdName());
                idField.set(object, id);
                for (Map.Entry<String, Class<?>> entry : bean.getPrimitiveTypeMap().entrySet()) {
                    Field field = aClass.getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    if (entry.getValue() == Long.class) {
                        Long param = primitiveResultSet.getLong(entry.getKey());
                        field.set(object, param);
                    } else {
                        String param = primitiveResultSet.getString(entry.getKey());
                        field.set(object, param);
                    }

                }
                result.put(id, object);
            }
            for (Map.Entry<Class<?>, String> entry : bean.getQueryBean().getClassAttachedQuery().entrySet()) {
                String query = entry.getValue();
                ResultSet resultSet = connection.createStatement().executeQuery(query);
                Map<Long, List<Object>> attachedClasses = new HashMap<>();

                while (resultSet.next()) {

                    Object attachedObject = entry.getKey().newInstance();
                    EntityBean attachedBean = entityBeanMap.get(attachedObject.getClass());
                    Long primaryId = resultSet.getLong(1);
                    Field idField = attachedObject.getClass().getDeclaredField(attachedBean.getIdName());
                    idField.setAccessible(true);
                    Long id = resultSet.getLong(attachedBean.getIdName());
                    idField.set(attachedObject, id);


                    for (Map.Entry<String, Class<?>> attachedEntry : attachedBean.getPrimitiveTypeMap().entrySet()) {

                        Field field = attachedObject.getClass().getDeclaredField(attachedEntry.getKey());
                        field.setAccessible(true);
                        if (attachedEntry.getValue() == Long.class) {
                            Long param = resultSet.getLong(attachedEntry.getKey());
                            field.set(attachedObject, param);
                        } else {
                            String param = resultSet.getString(attachedEntry.getKey());
                            field.set(attachedObject, param);
                        }

                    }
                    attachedClasses.computeIfAbsent(primaryId, k -> new ArrayList<>());
                    attachedClasses.get(primaryId).add(attachedObject);

                    for (Map.Entry<Long, List<Object>> attachedEntry : attachedClasses.entrySet()) {
                        Field field = result.get(primaryId).getClass().getDeclaredField(attachedObject.getClass().getSimpleName().toLowerCase(Locale.ROOT) + "_id");
                        field.setAccessible(true);
                        field.set(result.get(primaryId), attachedEntry.getValue().get(0));
                    }
                }


            }

            // многие ко многим

            for (Map.Entry<Class<?>, String> manyToManyEntry : bean.getQueryBean().getTableAttachedQuery().entrySet()) {
                EntityBean entityBean = entityBeanMap.get(manyToManyEntry.getKey());
                ResultSet resultSet = connection.createStatement().executeQuery(manyToManyEntry.getValue());
                Map<Long, List<Object>> objects = new HashMap<>();
                String columnName = "";
                while (resultSet.next()) {
                    Object attachedObject = manyToManyEntry.getKey().newInstance();
                    columnName = attachedObject.getClass().getSimpleName().toLowerCase(Locale.ROOT);
                    Field idField = attachedObject.getClass().getDeclaredField(entityBean.getIdName());
                    idField.setAccessible(true);
                    Long id = resultSet.getLong(entityBean.getIdName());
                    idField.set(attachedObject, id);
                    Long primaryId = resultSet.getLong(1);
                    for (Map.Entry<String, Class<?>> entry : entityBean.getPrimitiveTypeMap().entrySet()) {
                        Field field = manyToManyEntry.getKey().getDeclaredField(entry.getKey());
                        field.setAccessible(true);
                        if (entry.getValue() == Long.class) {
                            Long param = resultSet.getLong(entry.getKey());
                            field.set(attachedObject, param);
                        } else {
                            String param = resultSet.getString(entry.getKey());
                            field.set(attachedObject, param);
                        }

                    }

                    objects.computeIfAbsent(primaryId, k -> new ArrayList<>());
                    objects.get(primaryId).add(attachedObject);


                }

                for (Map.Entry<Long, List<Object>> attachedEntry : objects.entrySet()) {
                    Object object = result.get(attachedEntry.getKey());
                    Field progress = object.getClass().getDeclaredField(columnName);
                    progress.setAccessible(true);
                    progress.set(object, attachedEntry.getValue());
                }

            }

            return result.values().stream().toList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    private Object createSimpleClass() {
        return new Object();
    }

    @Override
    public void persist(Object o) {

    }

    @Override
    public <T> T merge(T t) {
        return null;
    }

    @Override
    public void remove(Object o) {

    }

    @Override
    public <T> T find(Class<T> aClass, Object o) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
        return null;
    }

    @Override
    public <T> T getReference(Class<T> aClass, Object o) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public void setFlushMode(FlushModeType flushModeType) {

    }

    @Override
    public FlushModeType getFlushMode() {
        return null;
    }

    @Override
    public void lock(Object o, LockModeType lockModeType) {

    }

    @Override
    public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {

    }

    @Override
    public void refresh(Object o) {

    }

    @Override
    public void refresh(Object o, Map<String, Object> map) {

    }

    @Override
    public void refresh(Object o, LockModeType lockModeType) {

    }

    @Override
    public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void detach(Object o) {

    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public LockModeType getLockMode(Object o) {
        return null;
    }

    @Override
    public void setProperty(String s, Object o) {

    }

    @Override
    public Map<String, Object> getProperties() {
        return null;
    }

    @Override
    public Query createQuery(String s) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return null;
    }

    @Override
    public Query createQuery(CriteriaUpdate criteriaUpdate) {
        return null;
    }

    @Override
    public Query createQuery(CriteriaDelete criteriaDelete) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createQuery(String s, Class<T> aClass) {
        return null;
    }

    @Override
    public Query createNamedQuery(String s) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String s, Class<T> aClass) {
        return null;
    }

    @Override
    public Query createNativeQuery(String s) {
        return null;
    }

    @Override
    public Query createNativeQuery(String s, Class aClass) {
        return null;
    }

    @Override
    public Query createNativeQuery(String s, String s1) {
        return null;
    }

    @Override
    public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
        return null;
    }

    @Override
    public void joinTransaction() {

    }

    @Override
    public boolean isJoinedToTransaction() {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    @Override
    public Object getDelegate() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public EntityTransaction getTransaction() {
        return null;
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
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
    public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
        return null;
    }

    @Override
    public EntityGraph<?> createEntityGraph(String s) {
        return null;
    }

    @Override
    public EntityGraph<?> getEntityGraph(String s) {
        return null;
    }

    @Override
    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
        return null;
    }
}
