package src.entity.create;


import src.entity.EntityBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AfterTableEntityBeanCreator extends EntityBeanCreator {

    @Override
    public List<String> doWork(EntityBean bean) {
        if (bean.getAfterTableMap().size() == 0) {
            return new ArrayList<>();
        }
        StringBuilder query = new StringBuilder();

        query.append(String.format("ALTER TABLE %s", bean.getTableName()));

        for (Map.Entry<String, Class<?>> entry : bean.getAfterTableMap().entrySet()) {
            query.append(String.format(" ADD COLUMN %s BIGINT,", entry.getKey()));
        }
        query.deleteCharAt(query.length() - 1);
        query.append(";");
        List<String> queries = new ArrayList<>();
        queries.add(query.toString());
        return queries;
    }
}
