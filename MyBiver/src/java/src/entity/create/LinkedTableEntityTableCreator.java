package src.entity.create;

import src.SortedPair;
import src.entity.EntityBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LinkedTableEntityTableCreator extends EntityBeanCreator {

    // Map<Pair<this.ClassName,attachedClass>, attachedClass>
    // CREATE TABLE this.ClassName_attachedClassName (
    // this.ClassName_id BIGINT REFERENCES this.ClassName (this.ClassName_id) ON UPDATE CASCADE ON DELETE CASCADE,
    // attachedClass_id BIGINT REFERENCES attachedClass (attachedClass_id) ON UPDATE CASCADE ON DELETE CASCADE,
    // CONSTRAINT this.ClassName_attachedClassName PRIMARY KEY (this.ClassName_id, attachedClass_id)
    // )
    @Override
    public List<String> doWork(EntityBean bean) {

        List<String> queries = new ArrayList<>();
        for (Map.Entry<SortedPair<String>, Class<?>> entry : bean.getNewTableMap().entrySet()) {
            StringBuilder query = new StringBuilder();
            String firstTableName = entry.getKey().key.substring(0, entry.getKey().key.length() - 3);
            String secondTableName = entry.getKey().value.substring(0, entry.getKey().value.length() - 3);

            query.append(String.format("CREATE TABLE %s_%s", firstTableName, secondTableName) );
            query.append("(");
            query.append(String.format(" %s BIGINT REFERENCES %s (%s_%s) ON UPDATE CASCADE ON DELETE CASCADE, ",
                    firstTableName + "_id", firstTableName,firstTableName ,"id"));
            query.append(String.format(" %s BIGINT REFERENCES %s (%s_%s) ON UPDATE CASCADE ON DELETE CASCADE, ",
                    secondTableName + "_id", secondTableName,secondTableName ,"id"));
            query.append(String.format(" CONSTRAINT %s_%s_pkey PRIMARY KEY (%s, %s)",
                    firstTableName, secondTableName, firstTableName + "_id", secondTableName + "_id"));
            query.append(");");

            queries.add(query.toString());
        }

        return queries;
    }

}
