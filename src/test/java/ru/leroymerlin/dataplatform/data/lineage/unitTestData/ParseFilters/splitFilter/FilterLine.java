package ru.leroymerlin.dataplatform.data.lineage.unitTestData.ParseFilters.splitFilter;

import java.util.List;

public class FilterLine {
    public String getFilter() {
        return filter;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public String filter;
    public List<String> answer;

    public FilterLine(String filter, List<String> ansver) {
        this.filter = filter;
        this.answer = ansver;
    }
    public FilterLine() { }
    public void setFilter(String filter) {
        this.filter = filter;
    }
    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }
    @Override
    public String toString() {
        return "FilterLine{" +
                "filter='" + filter + '\'' +
                ", ansver=" + answer +
                '}';
    }
}
