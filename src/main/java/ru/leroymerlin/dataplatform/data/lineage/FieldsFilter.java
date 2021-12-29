package ru.leroymerlin.dataplatform.data.lineage;

import java.util.List;

public class FieldsFilter {

    public void setValues(List<FilterValues> values) {
        this.values = values;
    }

    private String filter;
    private List<FilterValues> values;

    public FieldsFilter() {
    }

    public FieldsFilter(String filter, List<FilterValues> values) {
        this.filter = filter;
        this.values = values;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<FilterValues> getValues() {
        return values;
    }
    public void addFilterValue(FilterValues item) {
        this.values.add(item);
    }

}
