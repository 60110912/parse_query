package ru.leroymerlin.dataplatform.data.lineage;

import java.util.List;

public class TableFields {
    private String field;
    private List<FieldsFilter> filters;

    public TableFields() {
    }


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<FieldsFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<FieldsFilter> filters) {
        this.filters = filters;
    }
}
