package ru.leroymerlin.dataplatform.data.lineage;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс извлекает информацию из фильтров для дальнейшего использования.
 * pa
 */
public class ParseFilters {
    public static Logger logger = Logger.getLogger(ParseExplain.class.getName());

    public void setFilter(String filter) {
        this.filter = filter;
    }

    private String filter;

    public ParseFilters() {
    }

    public ParseFilters(String filter) {
        this.filter = filter;
    }

    /**
     * Function split filter line to some condition. Split filter equals operator "AND" or "OR".
     * https://crate.io/docs/sql-99/en/latest/chapters/29.html#from-clause
     *
     * @return List filter conditions.
     */
    public List<String> splitFilters() {
        // Для объединения условий можно использовать только два оператора AND и OR

        String clearFilter = unBracket(this.filter);
        logger.logp(Level.INFO, "test", "test", "UnBracket string" + clearFilter);
        String[] m = Pattern.compile("\\W{1}and\\W{1}|\\W{1}or\\W{1}", Pattern.CASE_INSENSITIVE).split(clearFilter);

        List<String> listFilters = new LinkedList<>();
        for (String s : m) {
            String tmp = unBracket((s.trim()));
            listFilters.add(tmp);
            logger.logp(Level.INFO,
                    this.getClass().getCanonicalName(),
                    "splitFilters",
                    "Add to filter list" + tmp);
        }
        return listFilters;
    }

    /**
     * Медод удалает лишние круглые скобки  в начале и в конце.
     *
     * @param str входная строка
     * @return str выходная строка без круглых скобок в начале и в конце.
     */
    public static String unBracket(String str) {
        Pattern pattern = Pattern.compile("^\\((.*)\\)$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return str;
    }

    /**
     * Медод удалает лишние круглые скобки  в начале и в конце.
     *
     * @param str входная строка
     * @return str выходная строка без круглых скобок в начале и в конце.
     */
    public static String unBracket(String str, String left, String right) {
        String regexp = "^" + left + "(.*)" +  right + "$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return str;
    }

    /**
     * Method return all filter value for next analise in PG.
     * @return List FiltersStruct.
     */
    public List<FiltersStruct> getFiltersValue() {
        List<String> filters = this.splitFilters();
        List<FiltersStruct> result = new <FiltersStruct>LinkedList();
        for (String filter : filters) {
            HashMap<String, String> valuesMap = parseFiltersStruct(filter);
            FiltersStruct fieldsFilter = new FiltersStruct();
            logger.logp(
                    Level.INFO,
                    this.getClass().getCanonicalName(),
                    "getFiltersValue",
                    "This valuesMap " + valuesMap
            );

            String valuesString = valuesMap.get("values");
            logger.logp(
                    Level.INFO,
                    this.getClass().getCanonicalName(),
                    "getFiltersValue",
                    "This valuesMap " + valuesMap
            );
            String valueType = ParseFilters.getValueType(valuesString);
            fieldsFilter.setFilter(filter);
            Pattern pattern = Pattern.compile("^ANY\\W", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(valuesString);
            if (matcher.find()){
                logger.logp(
                        Level.INFO,
                        this.getClass().getCanonicalName(),
                        "getFiltersValue",
                        "Match Any"
                );
                List<String> resAnyList = ParseFilters.getAnyList(valuesString);
                logger.logp(
                        Level.INFO,
                        this.getClass().getCanonicalName(),
                        "getFiltersValue",
                        "resAnyList" + resAnyList
                );
                for (String item: resAnyList){
                    String clearValue;
                    if (valueType.equals("text")) {
                        clearValue = ParseFilters.unBracket(item, "\'", "\'");
                    }
                    else {
                        clearValue = item;
                    }
                    fieldsFilter.addFilterValues(ParseFilters.unTypingValues(clearValue));
                }
            }
            else {
                String clearValue;
                if (valueType.equals("text")) {
                    clearValue = ParseFilters.unBracket(valuesString, "\'", "\'");
                }
                else {
                    clearValue = valuesString;
                }
                fieldsFilter.addFilterValues(ParseFilters.unTypingValues(clearValue));

            }
            fieldsFilter.setType(valueType);
            fieldsFilter.setValuesFromHashmap(valuesMap);
            result.add(fieldsFilter);
        }

        return result;
    }


    /**
     * Function get alias, field, operator and value from filters.
     *
     * @param str One of filter statement.
     * @return Map like json struct
     * {"field": "field_value",
     * "table": "table_value",
     * "operator": "operator_value",
     * "values": "values_value"
     * }
     */
    public HashMap<String, String> parseFiltersStruct(String str) {
        // "alias"."value" operator value
        HashMap<String, String> result = new HashMap<>();
        List<String> regList = new ArrayList<>();
        regList.add("^\\W*(?<table>\\w+)\\W*\\.\\W*?(?<field>\\w+)\\W*?(?<operator>[<>=!]+|IS)\\W(?<values>.*)$");
        regList.add("^\\W*(?<field>\\w+)\\W*?(?<operator>[<>=!]+|IS)\\W(?<values>.*)$");
        logger.logp(
                Level.INFO,
                this.getClass().getCanonicalName(),
                "parseFiltersStruct",
                "Source String=" + str
        );
        for (String s : regList) {
            Pattern pattern = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                logger.logp(
                        Level.INFO,
                        this.getClass().getCanonicalName(),
                        "parseFiltersStruct",
                        "Matcher" + matcher
                );
                result.put("field", matcher.group("field").trim());
                if (matcher.groupCount() == 4) {
                    result.put("table", matcher.group("table").trim());
                }

                result.put("operator", matcher.group("operator").trim());
                result.put("values", matcher.group("values").trim());
                logger.logp(
                        Level.INFO,
                        this.getClass().getCanonicalName(),
                        "parseFiltersStruct",
                        "Return list" + result
                );
                return result;
            }
        }
        return result;
    }

    /**
     * Function get value type from filter clause.
     * @param str - filter string
     * @return String type for pg rangetypes. https://postgrespro.ru/docs/postgrespro/9.5/rangetypes
     */
    public static String getValueType(String str){
        String valueType = "text";
        if (Pattern.compile("::int|::numeric|::double", Pattern.CASE_INSENSITIVE)
                .matcher(str)
                .find()) {
            return "numrange";
        }
        if (Pattern.compile("^\\d*$")
                .matcher(str.trim())
                .find()) {
            return "numrange";
        }
        if (Pattern.compile("::date|::time", Pattern.CASE_INSENSITIVE)
                .matcher(str)
                .find()){
           return  "tsrange";
        }
        return valueType;
    }

    /**
     * Function get values from ANY array struct.
     * @param str - string with Any array.
     * @return list values
     */
    public static List<String> getAnyList(String str){
        String regStr = "\\\'\\{(.*)\\}\\\'";
        Pattern pattern = Pattern.compile(regStr, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return Arrays.asList(matcher.group(1).split(",\\s*"));
        }
        return null;
    }

    public static String unTypingValues(String str) {
        List<String> regList = new ArrayList<>();
        regList.add("'(?<value>.*)'::");
        regList.add("\\s*(?<value>.*)::");

        for (String s : regList) {
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                return matcher.group("value");
            }
        }
        return str;
    }
}
