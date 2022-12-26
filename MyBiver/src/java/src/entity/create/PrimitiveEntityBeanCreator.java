package src.entity.create;

import src.entity.EntityBean;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrimitiveEntityBeanCreator extends EntityBeanCreator {

    @Override
    public List<String> doWork(EntityBean bean) {
        StringBuilder query = new StringBuilder();
        query.append(String.format("CREATE TABLE %s", bean.getTableName()));
        query.append(" ( ");
        query.append(String.format( " %s BIGINT PRIMARY KEY,", bean.getIdName()));
        for (Map.Entry<String, Class<?>> entry : bean.getPrimitiveTypeMap().entrySet()) {
            query.append(String.format(" %s %s,", entry.getKey(), typeToSql.get(entry.getValue())));
        }
        query.deleteCharAt(query.length() - 1);
        query.append(" );");
        List<String> queries = new ArrayList<>();
        queries.add(query.toString());
        return queries;
    }
}
