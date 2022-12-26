package src.entity.create;


import src.entity.EntityBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EntityBeanCreator {

    Map<Class<?>, String> typeToSql = new HashMap<>();


    EntityBeanCreator() {
        typeToSql.put(String.class, "VARCHAR(255)");
        typeToSql.put(Long.class, "BIGINT");

    }

    protected List<String> doWork(EntityBean bean) {
        return new ArrayList<>();
    }


}
