package ru.leroymerlin.dataplatform.data.lineage.unitTestData.ParseFilters.splitFilter;

import java.util.List;

public class FilterStruct {
    public List<FilterLine> getSplitFilters() {
        return splitFilters;
    }

    public String getTestName() {
        return testName;
    }

    private List<FilterLine> splitFilters;

    public void setSplitFilters(List<FilterLine> splitFilters) {
        this.splitFilters = splitFilters;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    private String testName;
    public FilterStruct(List<FilterLine> filter){
        this.splitFilters = filter;
    }

    public FilterStruct() {
    }
}
