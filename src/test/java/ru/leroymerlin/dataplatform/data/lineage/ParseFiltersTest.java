package ru.leroymerlin.dataplatform.data.lineage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import com.fasterxml.jackson.dataformat.yaml.*;
import ru.leroymerlin.dataplatform.data.lineage.unitTestData.ParseFilters.splitFilter.FilterLine;
import ru.leroymerlin.dataplatform.data.lineage.unitTestData.ParseFilters.splitFilter.FilterStruct;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

    @Test
    public void testSplitFilters() throws Exception {
        FilterStruct testObject = new FilterStruct();
        testObject = getUnitData(testObject,
                "src/test/java/ru/leroymerlin/dataplatform/data/lineage/unitTestData/ParseFilters/splitFilter/data.yaml");
        List<FilterLine> testUnit = testObject.getSplitFilters();
        testUnit.forEach(n ->  assertEquals(n.getAnsver(), new ParseFilters(n.getFilter()).splitFilters()));
    }
    @Test
    public void testParseFiltersStruct() throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        Map readValue = mapper.readValue(new File("src/test/java/ru/leroymerlin/dataplatform/data/lineage/unitTestData/parseFiltersStruct/data.yaml"), Map.class);
        List<HashMap<String, Object>> testUnit = (List<HashMap<String, Object>>) readValue.get("filtersValue");
        for (var elem : testUnit) {
            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testParseFiltersStruct", "Start for:"+ elem.toString());
            //System.out.println(elem.get("filter").toString());
            HashMap<String, String> methodAnswers =
                    new ParseFilters()
                            .parseFiltersStruct(elem.get("filter").toString());
            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testParseFiltersStruct", "Function ansver:"+methodAnswers.toString());
            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testParseFiltersStruct", "Yaml ansver:"+ ((HashMap<String, String>) elem.get("ansver")).toString());
            boolean testResult = methodAnswers.equals((HashMap<String, String>)elem.get("ansver"));
            logger.logp(Level.INFO, this.getClass().getCanonicalName(), "testParseFiltersStruct", "Result test:" + testResult);
            assertTrue(methodAnswers.equals((HashMap<String, String>)elem.get("ansver")));

        }
    }

}