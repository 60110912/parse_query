package ru.leroymerlin.dataplatform.data.lineage;

public class FilterValues {


    private String type;
    private String value;

    public FilterValues(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public FilterValues() { }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
