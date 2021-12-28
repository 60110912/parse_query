package ru.leroymerlin.dataplatform.data.lineage.unitTestData.ParseFilters.splitFilter;

import java.util.List;

public class FilterLine {
    public String getFilter() {
        return filter;
    }

    public List<String> getAnsver() {
        return ansver;
    }

    public String filter;
    public List<String> ansver;

    public FilterLine(String filter, List<String> ansver) {
        this.filter = filter;
        this.ansver = ansver;
    }
    public FilterLine() { }
    public void setFilter(String filter) {
        this.filter = filter;
    }
    public void setAnsver(List<String> ansver) {
        this.ansver = ansver;
    }
    @Override
    public String toString() {
        return "FilterLine{" +
                "filter='" + filter + '\'' +
                ", ansver=" + ansver +
                '}';
    }
}
