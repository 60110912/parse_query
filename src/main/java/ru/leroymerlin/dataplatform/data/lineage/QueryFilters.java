package ru.leroymerlin.dataplatform.data.lineage;

import java.util.List;

public class QueryFilters {
    private String filters;

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public List<FiltersStruct> getFilters_value() {
        return filters_value;
    }

    public void setFilters_value(List<FiltersStruct> filters_value) {
        this.filters_value = filters_value;
    }

    private List<FiltersStruct> filters_value;

    @Override
    public String toString() {
        return "QueryFilters{" +
                "filters='" + filters + '\'' +
                ", filters_value=" + filters_value.toString() +
                '}';
    }
}
