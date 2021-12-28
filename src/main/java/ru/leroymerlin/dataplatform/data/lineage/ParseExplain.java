package ru.leroymerlin.dataplatform.data.lineage;

import com.jayway.jsonpath.*;
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
    public static void main(String[] args) throws java.io.FileNotFoundException, net.sf.jsqlparser.JSQLParserException {
        String resourceName = "C:\\Users\\60110912\\IdeaProjects\\parse_query\\src\\main\\java\\ru\\leroymerlin\\dataplatform\\data\\lineage\\test.json";
        List<String> test = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(resourceName))) {
            stream.forEach(line-> test.add(line));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger logger = Logger.getLogger( ParseExplain.class.getName());
        String test_json = test.get(0)
                .replace("\\\\n", " ")
                .replace("\\n\\t","")
                .replace("\\\"", "\"")
                .replace("\\\\\"","\\\"");

//        logger.logp(Level.INFO, "test", "test", test_json);
        JSONObject object = new JSONObject(test_json);
//        logger.logp(Level.INFO, "test", "test", object.get("Query Text").toString());

        Statement statement = CCJSqlParserUtil.parse(object.get("Query Text").toString());
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
        tableList.forEach(n ->  logger.logp(Level.INFO, "test", "test", n));

//        Подумать над более эффективной реализацией сериализации.
        List<net.minidev.json.JSONArray> plans = JsonPath.read(
                object.toString(),
                "$..['Plans']");
        Integer sizePlans = plans.size();
        logger.logp(Level.INFO, "test", "new", sizePlans.toString());

        HashMap<String, String> aliasMap = new HashMap<>();
        logger.logp(Level.INFO, "test", "test", "Reverse get plans" );
        for(int i = plans.size(); i-- > 0;) {
            net.minidev.json.JSONArray CurrentElement= plans.get(i);
            logger.logp(Level.INFO, "test", "test",  CurrentElement.toString() );
            // Добавить цикл обработки данных по каждому индексу.
            java.util.LinkedHashMap TableRelation = JsonPath.read(
                    CurrentElement.get(0),
                    "$.['Alias','Schema' , 'Relation Name', 'Filter']"
            );
            logger.logp(Level.INFO, "test", "list hash map", TableRelation.toString());
//            for (Object key : TableRelation.keySet()) {
//                logger.logp(Level.INFO, "test", "test", "Key:" + key.toString() + " " + TableRelation.get(key));
//            }
//            logger.logp(Level.INFO, "test", "test", "Table ralation" );
            //TableRelation.forEach(n-> logger.logp(Level.INFO, "test", "test",  "!!!!!" + n.toString()));
//            logger.logp(Level.INFO, "test", "test", "!!!" + CurrentElement.get(0).toString());
        }

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
