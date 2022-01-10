package ru.leroymerlin.dataplatform.data.lineage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import com.fasterxml.jackson.dataformat.yaml.*;
import ru.leroymerlin.dataplatform.data.lineage.unitTestData.ParseFilters.splitFilter.FilterLine;
import ru.leroymerlin.dataplatform.data.lineage.unitTestData.ParseFilters.splitFilter.FilterStruct;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class ParseFiltersTest {
    public static Logger logger = Logger.getLogger( ParseFiltersTest.class.getName());

    // private ParseFilters parceFilter;
    @BeforeClass
    public static void beforeClass() {
        System.out.println("Before CalculatorTest.class");
    }

    @AfterClass
    public  static void afterClass() {
        System.out.println("After CalculatorTest.class");
    }
    @Before
    public void initTest() {
        System.out.println("Init test");
//        ParseFilters test = new ParseFilters();
    }


    public <T> T getUnitData(T classObject, String file) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        classObject = (T) mapper.readValue(
                new File(file),
                classObject.getClass()
        );
        return (T) classObject;

    }

    @After
    public void afterTest() {
        //parceFilter = null;
    }

//    @Test
//    public void testSplitFilters() throws Exception {
//        FilterStruct testObject = new FilterStruct();
//        testObject = getUnitData(testObject,
//                "src/test/java/ru/leroymerlin/dataplatform/data/lineage/unitTestData/ParseFilters/splitFilter/data.yaml");
//        List<FilterLine> testUnit = testObject.getSplitFilters();
//        testUnit.forEach(n ->  assertEquals(n.getAnsver(), new ParseFilters(n.getFilter()).splitFilters()));
//    }
//    @Test
//    public void testParseFiltersStruct() throws Exception {
//        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        mapper.findAndRegisterModules();
//        Map readValue = mapper.readValue(
//                new File("src/test/java/ru/leroymerlin/dataplatform/data/lineage/unitTestData/ParseFilters/parseFiltersStruct/data.yaml"),
//                Map.class
//        );
//        List<HashMap<String, Object>> testUnit = (List<HashMap<String, Object>>) readValue.get("filtersValue");
//        for (HashMap<String, Object> elem : testUnit) {
//            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testParseFiltersStruct", "Start for:"+ elem.toString());
//            HashMap<String, String> methodAnswers =
//                    new ParseFilters()
//                            .parseFiltersStruct(elem.get("filter").toString());
//            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testParseFiltersStruct", "Function ansver:"+methodAnswers.toString());
//            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testParseFiltersStruct", "Yaml ansver:"+ ((HashMap<String, String>) elem.get("ansver")).toString());
//            boolean testResult = methodAnswers.equals((HashMap<String, String>)elem.get("ansver"));
//            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testParseFiltersStruct", "Result test:" + testResult);
//            assertTrue(methodAnswers.equals((HashMap<String, String>)elem.get("ansver")));
//
//        }
//    }
    @Test
    public void testGetValueType() throws IOException {
        logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testGetValueType", "Start testGetValueType");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        Map readValue = mapper.readValue(
                new File("src/test/java/ru/leroymerlin/dataplatform/data/lineage/unitTestData/ParseFilters/getValueType/data.yaml"),
                Map.class
        );
        List<HashMap<String, String>> testUnit = (List<HashMap<String, String>>) readValue.get("valueType");
        testUnit.forEach(n ->  assertEquals(
                n.get("ansver"),
                ParseFilters.getValueType(n.get("value"))
                )
        );
    }
//    @Test
//    public void testGetAnyList() throws IOException {
//        logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testGetAnyList", "Start testGetValueType");
//        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        mapper.findAndRegisterModules();
//        Map readValue = mapper.readValue(
//                new File("src/test/java/ru/leroymerlin/dataplatform/data/lineage/unitTestData/ParseFilters/getAnyList/data.yaml"),
//                Map.class
//        );
//        List<HashMap<String, Object>> testUnit = (List<HashMap<String, Object>>) readValue.get("anyList");
//        for (HashMap<String, Object> elem : testUnit) {
//            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testGetAnyList", "Start for:"+ elem.toString());
//            List<String> methodAnswers = ParseFilters.getAnyList(
//                    elem.get("value").toString()
//            );
//            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testGetAnyList", "Function ansver:"+methodAnswers.toString());
//            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testGetAnyList", "Yaml ansver:"+ ((List<String>) elem.get("ansver")).toString());
//            boolean testResult = methodAnswers.containsAll((List<String>)elem.get("ansver"));
//            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testGetAnyList", "Result test:" + testResult);
//            assertTrue(testResult);
//
//        }
//
//    }
//    @Test
//    public void testGetFiltersValue() throws IOException {
//        logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testGetFiltersValue", "Start testGetFiltersValue");
//        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        mapper.findAndRegisterModules();
//        Map readValue = mapper.readValue(
//                new File("src/test/java/ru/leroymerlin/dataplatform/data/lineage/unitTestData/ParseFilters/getFiltersValue/data.yaml"),
//                Map.class
//        );
//        List<HashMap<String, Object>> testUnit = (List<HashMap<String, Object>>) readValue.get("filterStruct");
//        for (HashMap<String, Object> elem : testUnit) {
//            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testGetFiltersValue", "Start for:"+ elem.toString());
//            ParseFilters unitTest = new ParseFilters(elem.get("filters").toString());
//            LinkedList<FiltersStruct> methodAnswers = (LinkedList<FiltersStruct>) unitTest.getFiltersValue();
//            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testGetFiltersValue", "Function ansver:"+methodAnswers.toString());
//            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testGetFiltersValue", "Yaml ansver:"+ ((List<FiltersStruct>) elem.get("ansver")).toString());
//            boolean testResult = methodAnswers.containsAll((List<FiltersStruct>)elem.get("ansver"));
//            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testGetFiltersValue", "Result test:" + testResult);
//            //assertTrue(testResult);
//
//        }
//
//    }
}