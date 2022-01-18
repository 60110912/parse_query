package ru.leroymerlin.dataplatform.data.lineage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExplainObject {


    private UUID query_id;
    private List<HashMap<String,String>> tables_sql;
    private List<HashMap<String,String>> table_explain;
    private String query_text;
    private List<QueryFilters> query_filters;

    public UUID getQuery_id() {
        return query_id;
    }

    public List<HashMap<String,String>> getTables_sql() {
        return tables_sql;
    }

    public void setTables_sql(List<HashMap<String,String>> tables_sql) {
        this.tables_sql = tables_sql;
    }

    public List<HashMap<String,String>> getTable_explain() {
        return table_explain;
    }

    public void setTable_explain(List<HashMap<String,String>> table_explain) {
        this.table_explain = table_explain;
    }

    public String getQuery_text() {
        return query_text;
    }

    public void setQuery_text(String query_text) {
        this.query_text = query_text;
    }

    public List<QueryFilters> getQuery_filters() {
        return query_filters;
    }

    public void setQuery_filters(List<QueryFilters> query_filters) {
        this.query_filters = query_filters;
    }

    public void addTables_sql(String table) {
        HashMap<String, String> result = new HashMap<>();
        Pattern pattern = Pattern.compile("\\W*(?<schema>\\w+)\\W*\\.\\W*?(?<table>\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(table);
        if (matcher.find()) {
            result.put("schema", matcher.group("schema"));
            result.put("table", matcher.group("table"));
        }
        else {
            result.put("schema", "public");
            result.put("table", table);
        }
        this.tables_sql.add(result);
    }
    public  void addTabel_explain(HashMap<String,String> map){
        HashMap<String, String> result = new HashMap<>();
        result.put("schema", map.get("Schema"));
        result.put("table" , map.get("Relation Name"));
        this.table_explain.add(result);
    }

    public void addQuery_filters(QueryFilters item){
        this.query_filters.add(item);
    }


    public ExplainObject() {
        this.query_id = UUID.randomUUID();
        this.tables_sql = new ArrayList<>();
        this.table_explain = new ArrayList<>() ;
        this.query_filters = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ExplainObject{" +
                "query_id=" + query_id +
                ", tables_sql=" + tables_sql +
                ", table_explain=" + table_explain +
                ", query_text='" + query_text + '\'' +
                ", query_filters=" + query_filters.toString() +
                '}';
    }
}
