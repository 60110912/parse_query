package ru.leroymerlin.dataplatform.data.lineage;

import java.util.List;

public class DBTable {
    private String schema;
    private String table;
    private List<TableFields> fields;

    public DBTable(String schema, String table, List<TableFields> fields) {
        this.schema = schema;
        this.table = table;
        this.fields = fields;
    }

    public DBTable() {    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<TableFields> getFields() {
        return fields;
    }

    public void setFields(List<TableFields> fields) {
        this.fields = fields;
    }
}
