package src.entity;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import src.SortedPair;

import javax.persistence.*;

import java.lang.reflect.Field;
import java.util.*;

public class EntityBeanProcessor {


    public EntityBeanProcessor() {

    }

    private Set<Class<?>> getEntityClassFromSource(String prefix) {
        Reflections reflections = new Reflections(prefix, new TypeAnnotationsScanner());
        return reflections.getTypesAnnotatedWith(Entity.class);
    }

    public Map<Class<?>, EntityBean> createEntityBeanMap(String prefix) {
        Set<Class<?>> classSet = getEntityClassFromSource(prefix);
        Map<Class<?>, EntityBean> result = new HashMap<>();
        for (Class<?> aClass : classSet) {
            result.put(aClass, createEntityBean(aClass));
        }
        for (Map.Entry<Class<?>, EntityBean> entry : result.entrySet()) {
            createReadQuery(entry.getValue());
        }
        return result;
    }

    private EntityBean createEntityBean(Class<?> aClass) {
        EntityBean bean = new EntityBean();
        Table tableAnnotation = aClass.getAnnotation(Table.class);

        bean.setTableName(tableAnnotation == null ? aClass.getSimpleName() : tableAnnotation.name());
        String idName = bean.getTableName() + "_id";
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            OneToOne oneToOneAnnotation = field.getAnnotation(OneToOne.class);
            ManyToOne manyToOneAnnotation = field.getAnnotation(ManyToOne.class);
            OneToMany oneToManyAnnotation = field.getAnnotation(OneToMany.class);
            ManyToMany manyToManyAnnotation = field.getAnnotation(ManyToMany.class);
            Id idAnnotation = field.getAnnotation(Id.class);
            Column columnAnnotation = field.getAnnotation(Column.class);

            if (idAnnotation != null) {
               bean.setIdName(idName);
            } else if (oneToOneAnnotation != null || manyToOneAnnotation != null) {
                bean.addAfterTable(field.getType().getSimpleName().toLowerCase(Locale.ROOT) + "_id", field.getType());
            } else if (oneToManyAnnotation != null || manyToManyAnnotation != null) {
                JoinTable joinTableAnnotation = field.getAnnotation(JoinTable.class);
                String firstMapping = joinTableAnnotation.joinColumns()[0].name();
                String secondMapping = joinTableAnnotation.inverseJoinColumns()[0].name();
                SortedPair<String> pair = new SortedPair<>(firstMapping, secondMapping);


                // мдаааааааааааааааааааа
                String typeName = field.getGenericType().getTypeName();
                int first = typeName.indexOf('<');
                int last = typeName.indexOf('>');
                try {
                    Class<?> clazz = Class.forName(typeName.substring(first + 1, last));
                    bean.addNewTable(pair, clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            } else {
                bean.addPrimitive(columnAnnotation == null ? field.getName().toLowerCase(Locale.ROOT) : columnAnnotation.name(),
                        field.getType());
            }
        }

        return bean;
    }

    private void createReadQuery(EntityBean bean) {
        bean.setQueryBean(new QueryBean(bean));
    }


}
