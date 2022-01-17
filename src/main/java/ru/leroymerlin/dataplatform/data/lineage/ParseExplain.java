package ru.leroymerlin.dataplatform.data.lineage;

import com.jayway.jsonpath.*;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.FileInputStream;
import java.util.List;
import java.io.FileReader;

import org.json.JSONTokener;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.stream.Stream;
import java.util.*;
import java.util.HashMap;
import java.util.logging.Logger;
//import net.sf.jsqlparser.statement;
//import net.sf.jsqlparser.statement.select;


public class ParseExplain {
    private HashMap<String, HashMap<String,String>> tableDict;
    Logger logger = Logger.getLogger( ParseExplain.class.getName());

    public JSONObject getExplainObject(JSONObject object) throws JSQLParserException {
        List<net.minidev.json.JSONArray> plans = JsonPath.read(
                object.toString(),
                "$..['Plans']");
        this.tableDict = new HashMap<>();
        Integer sizePlans = plans.size();
        logger.logp(Level.INFO, "test", "new", sizePlans.toString());

        Statement statement = CCJSqlParserUtil.parse(object.get("Query Text").toString());
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        ExplainObject currExplain = new ExplainObject();
        tablesNamesFinder.getTableList(selectStatement).forEach(n-> currExplain.addTables_sql(n));

        logger.logp(Level.INFO, "test", "test", tablesNamesFinder.getTableList(selectStatement).toString() );

        HashMap<String, String> aliasMap = new HashMap<>();
        logger.logp(Level.INFO, "test", "test", "Reverse get plans" );
        for(int i = plans.size(); i-- > 0;) {
            net.minidev.json.JSONArray CurrentElement= plans.get(i);
            logger.logp(Level.INFO, "test", "test",  CurrentElement.toString() );
            // Добавить цикл обработки данных по каждому индексу.
            LinkedHashMap TableRelation = JsonPath.read(
                    CurrentElement.get(0),
                    "$.['Alias','Schema' , 'Relation Name', 'Filter']"
            );
            try {
                this.tableDict.put(TableRelation.get("Alias").toString(), (HashMap<String,String>) TableRelation);
                currExplain.addTabel_explain((HashMap<String,String>) TableRelation);
            }
            catch ( NullPointerException e){}
            try {
                String currFilter = TableRelation.get("Filter").toString();
                ParseFilters filterValue = new ParseFilters(currFilter);
                QueryFilters toResult = new QueryFilters();
                toResult.setFilters(currFilter);
                toResult.setFilters_value(filterValue.getFiltersValue());
                currExplain.addTabel_explain((HashMap<String,String>) TableRelation);
            }
            catch ( NullPointerException e){}
            
            logger.logp(Level.INFO, "test", "list hash map", TableRelation.toString());

        }
//        logger.logp(Level.INFO, "test", "list hash map", currExplain.toString());
        return object;

    }

    public static JSONObject convertStringToJson(String str){
        String test_json = str
                .replace("\\\\n", " ")
                .replace("\\n\\t","")
                .replace("\\\"", "\"")
                .replace("\\\\\"","\\\"");
        return new JSONObject(test_json);
    }

    public ParseExplain() {
    }

    public static void main(String[] args) throws java.io.FileNotFoundException, net.sf.jsqlparser.JSQLParserException {
        String resourceName = "C:\\Users\\60110912\\IdeaProjects\\parse_query\\src\\main\\java\\ru\\leroymerlin\\dataplatform\\data\\lineage\\test.json";
//        List<String> test = new ArrayList<>();
//        try (Stream<String> stream = Files.lines(Paths.get(resourceName))) {
//            stream.forEach(line-> test.add(line));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String test_json = test.get(0)
//                .replace("\\\\n", " ")
//                .replace("\\n\\t","")
//                .replace("\\\"", "\"")
//                .replace("\\\\\"","\\\"");
//
////        logger.logp(Level.INFO, "test", "test", test_json);
//        JSONObject object = new JSONObject(test_json);
//        logger.logp(Level.INFO, "test", "test", object.get("Query Text").toString());

//        Statement statement = CCJSqlParserUtil.parse(object.get("Query Text").toString());
//        Select selectStatement = (Select) statement;
//        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
//        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
//        tableList.forEach(n ->  logger.logp(Level.INFO, "test", "test", n));

//        Подумать над более эффективной реализацией сериализации.


//        plans.forEach(n -> logger.logp(Level.INFO, "test", "test",  n.toString()));



        // Adding elements to the Map
        // using standard add() method
//        map.put("vishal", 10);
//        map.put("sachin", 30);
//        map.put("vaibhav", 20);
//        String myString = new JSONObject()
//                .put("JSON", "Hello, World!").toString();
//        authors.forEach(System.out::println);
//        Object document = Configuration.defaultConfiguration().jsonProvider().parse("{\"Hallo\": \"World!\"}");
//
//        System.out.println(document.toString());
//        JSONObject myJson = new JSONObject()
//                .put("JSON", "Hello, World!");
//        System.out.println(myJson.toString());

    }
}
