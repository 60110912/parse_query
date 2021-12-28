package ru.leroymerlin.dataplatform.data.lineage.testIdea;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;



public class YamlMapParser {

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        // InputStream resource = YamlMapParser.class.getResourceAsStream("C:\\Users\\60110912\\IdeaProjects\\parse_query\\src\\test\\java\\ru\\leroymerlin\\dataplatform\\data\\lineage\\unitTestData\\ParseFilters\\splitFilter\\data.yaml");
        Map readValue = mapper.readValue(new File("C:\\\\Users\\\\60110912\\\\IdeaProjects\\\\parse_query\\\\src\\\\test\\\\java\\\\ru\\\\leroymerlin\\\\dataplatform\\\\data\\\\lineage\\\\unitTestData\\\\ParseFilters\\\\splitFilter\\\\data.yaml"), Map.class);
        System.out.println(readValue.get("splitFilters"));
        System.out.println(readValue.get("splitFilters").getClass());
    }
}

