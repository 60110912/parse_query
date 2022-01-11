package ru.leroymerlin.dataplatform.data.lineage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FiltersStruct {


    private String filter;
    private String table;
    private String field;
    private String operator;
    private String type;
    private List<String> values;
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public FiltersStruct(String filter, String table, String field, String operator, String type, List<String> values) {
        this.filter = filter;
        this.table = table;
        this.field = field;
        this.operator = operator;
        this.type = type;
        this.values = values;
    }

    public FiltersStruct() {
        this.values = new LinkedList<>();
    }
    public void addFilterValues(String elem)
    {
        this.values.add(elem);
    }
    public void setValuesFromHashmap (HashMap elem){
        this.field = elem.get("field").toString();
        try {
            this.table = elem.get("table").toString();
        }
        catch ( NullPointerException e)
        {
            this.table = null;
        }
        this.operator = elem.get("operator").toString();
    }
    public FiltersStruct(HashMap elem) {
        this.filter = elem.get("filter").toString() ;
        try {
            this.table = elem.get("table").toString();
        }
        catch ( NullPointerException e)
        {
            this.table = null;
        }
        this.field = elem.get("field").toString();
        this.operator = elem.get("operator").toString();
        this.type = elem.get("type").toString();
        this.values = (List<String>)elem.get("values");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FiltersStruct)) return false;
        FiltersStruct that = (FiltersStruct) o;
        return Objects.equals(filter, that.filter)
                && Objects.equals(table, that.table)
                && Objects.equals(field, that.field)
                && Objects.equals(operator, that.operator)
                && Objects.equals(type, that.type)
                && ((FiltersStruct) o).getValues().containsAll(that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filter, table, field, operator, type, values);
    }

    @Override
    public String toString() {
        return "FiltersStruct{" +
                "filter='" + filter + '\'' +
                ", table='" + table + '\'' +
                ", field='" + field + '\'' +
                ", operator='" + operator + '\'' +
                ", type='" + type + '\'' +
                ", values=" + values +
                '}';
    }
}
