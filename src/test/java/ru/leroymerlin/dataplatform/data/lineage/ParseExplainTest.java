package ru.leroymerlin.dataplatform.data.lineage;

import junit.framework.TestCase;
import net.sf.jsqlparser.JSQLParserException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ParseExplainTest extends TestCase {
    public static Logger logger = Logger.getLogger( ParseExplainTest.class.getName());
    @Test
    public void testConvertStringToJson() {
        String fileName = "src/test/java/ru/leroymerlin/dataplatform/data/lineage/unitTestData/ParseExplain/convertStringToJson/test.txt";
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()){
                try {
                    ParseExplain.convertStringToJson(scanner.nextLine());
                    assertTrue(true);
                } catch (JSONException e){
                    assertTrue(false);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetExplainObject() {
        String fileName = "src/test/java/ru/leroymerlin/dataplatform/data/lineage/unitTestData/ParseExplain/convertStringToJson/test.txt";
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()){
                JSONObject currentElem = ParseExplain.convertStringToJson(scanner.nextLine());
                logger.logp(
                        Level.INFO,
                        this.getClass().getCanonicalName(),
                        "testGetExplainObject",
                        "elem:"+ currentElem.toString()
                );
                try {
                    ParseExplain currentExplain = new ParseExplain();
                    JSONObject explain = currentExplain.getExplainObject(currentElem);
                    logger.logp(
                            Level.INFO,
                            this.getClass().getCanonicalName(),
                            "testGetExplainObject",
                            "explain:"+ explain.toString()
                    );
                    assertTrue(true);
                } catch (JSONException e){
                    assertTrue(false);
                    e.printStackTrace();
                }
            }
        } catch (IOException | JSQLParserException e) {
            e.printStackTrace();
        }
    }
}