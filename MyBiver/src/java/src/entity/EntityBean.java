package src.entity;

import src.SortedPair;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EntityBean {

    String tableName;

    String idName;

    QueryBean queryBean;


    // Map<ColumnName, Type>
    // into CREATE TABLE query
    private final Map<String, Class<?>> primitiveTypeMap = new LinkedHashMap<>();

    // Map<joinColumnName,attachedClass>
    // AFTER TABLE ${tableName} ADD COLUMN '${ClassName}_id' INT
    private final Map<String, Class<?>> afterTableMap = new LinkedHashMap<>();

    // Map<Pair<this.ClassName,attachedClassName>, attachedClass>
    // CREATE TABLE this.ClassName_attachedClassName (
    // this.ClassName_id BIGINT REFERENCES this.ClassName (this.ClassName_id) ON UPDATE CASCADE ON DELETE CASCADE,
    // attachedClass_id BIGINT REFERENCES attachedClass (attachedClass_id) ON UPDATE CASCADE ON DELETE CASCADE,
    // CONSTRAINT this.ClassName_attachedClassName PRIMARY KEY (this.ClassName_id, attachedClass_id)
    // )
    private final Map<SortedPair<String>, Class<?>> newTableMap = new LinkedHashMap<>();


    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public void addPrimitive(String columnName, Class<?> type) {
        primitiveTypeMap.put(columnName, type);
    }

    public void addAfterTable(String columnName, Class<?> aClass) {
        afterTableMap.put(columnName, aClass);
    }

    public void addNewTable(SortedPair<String> pair, Class<?> aClass) {
        newTableMap.put(pair, aClass);
    }

    public Map<String, Class<?>> getPrimitiveTypeMap() {
        return primitiveTypeMap;
    }

    public Map<String, Class<?>> getAfterTableMap() {
        return afterTableMap;
    }

    public Map<SortedPair<String>, Class<?>> getNewTableMap() {
        return newTableMap;
    }


    public String getTableName() {
        return tableName;
    }

    public QueryBean getQueryBean() {
        return queryBean;
    }

    public void setQueryBean(QueryBean queryBean) {
        this.queryBean = queryBean;
    }


    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

}
