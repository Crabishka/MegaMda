package src.entity;

import src.SortedPair;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QueryBean {

    String simpleSelectQuery;

    Map<Class<?>, String> classAttachedQuery;

    Map<Class<?>, String> tableAttachedQuery;

    public QueryBean(EntityBean bean) {
        simpleSelectQuery = createSimpleSelectQuery(bean);
        classAttachedQuery = createClassAttachedQuery(bean);
        tableAttachedQuery = createTableAttachedQuery(bean);
    }

    private String createSimpleSelectQuery(EntityBean bean) {
        return String.format("SELECT * FROM %s;", bean.getTableName());
    }


    // <AttachedClass,Query>
    private Map<Class<?>, String> createClassAttachedQuery(EntityBean bean) {
        Map<Class<?>, String> map = new HashMap<>();
        for (Map.Entry<String, Class<?>> entry : bean.getAfterTableMap().entrySet()) {
            StringBuilder query = new StringBuilder();
            String thisTableName = bean.getTableName();
            String attachedTableName = entry.getValue().getSimpleName().toLowerCase(Locale.ROOT);
            String firstLetter = attachedTableName.substring(0, 1);
            String attachedTableParam = entry.getKey();

            query.append(String.format("SELECT * FROM %s join %s %s on %s.%s = %s.%s; ",
                    thisTableName, attachedTableName, firstLetter, thisTableName, attachedTableParam, firstLetter, attachedTableParam));
            map.put(entry.getValue(), query.toString());
        }
        return map;
    }

    private Map<Class<?>, String> createTableAttachedQuery(EntityBean bean) {
        Map<Class<?>, String> map = new HashMap<>();
        for (Map.Entry<SortedPair<String>, Class<?>> entry : bean.getNewTableMap().entrySet()) {
            StringBuilder query = new StringBuilder();
            String thisTableName = bean.getTableName();

            String firstTableName = entry.getKey().key.substring(0, entry.getKey().key.length() - 3);
            String secondTableName = entry.getKey().value.substring(0, entry.getKey().value.length() - 3);
            String joinedTable;
            if (firstTableName.equals(thisTableName)) {
                joinedTable = secondTableName;
            } else {
                joinedTable = firstTableName;
            }

            String abbreviation = firstTableName.substring(0, 1) + secondTableName.substring(0, 1);
            String linkedTableName = firstTableName + "_" + secondTableName;
            query.append(String.format("SELECT * FROM  %s, %s join %s %s on %s.%s_id = %s.%s_id;",
                    thisTableName, joinedTable, linkedTableName, abbreviation, joinedTable, joinedTable, abbreviation, joinedTable));
            map.put(entry.getValue(), query.toString());
        }
        return map;
    }

    @Override
    public String toString() {
        return "QueryBean{" +
                "simpleSelectQuery='" + simpleSelectQuery + '\'' +
                ", classAttachedQuery=" + classAttachedQuery +
                ", tableAttachedQuery=" + tableAttachedQuery +
                '}';
    }

    public String getSimpleSelectQuery() {
        return simpleSelectQuery;
    }

    public void setSimpleSelectQuery(String simpleSelectQuery) {
        this.simpleSelectQuery = simpleSelectQuery;
    }

    public Map<Class<?>, String> getClassAttachedQuery() {
        return classAttachedQuery;
    }

    public void setClassAttachedQuery(Map<Class<?>, String> classAttachedQuery) {
        this.classAttachedQuery = classAttachedQuery;
    }

    public Map<Class<?>, String> getTableAttachedQuery() {
        return tableAttachedQuery;
    }

    public void setTableAttachedQuery(Map<Class<?>, String> tableAttachedQuery) {
        this.tableAttachedQuery = tableAttachedQuery;
    }

}
