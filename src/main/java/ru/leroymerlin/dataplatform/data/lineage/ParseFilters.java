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
     * Function split filter line to some condition. Split filter equals operator AND or OR.
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
        while (matcher.find()) {
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
    public static String unBracket(String str, String left, String rigth) {
        String regexp = "^" + left + "(.*)" +  rigth + "$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            return matcher.group(1);
        }
        return str;
    }

    public FilterValues getFiltersValue() {
        List<String> filters = this.splitFilters();
        List<DBTable> resultTables = new LinkedList<>();
        for (String filter : filters) {
            HashMap<String, String> valuesMap = parseFiltersStruct(filter);
            FieldsFilter fieldsFilter = new FieldsFilter();
            logger.logp(
                    Level.INFO,
                    this.getClass().getCanonicalName(),
                    "unBracket",
                    "This add to filter list" + valuesMap.toString()
            );

            String valuesString = valuesMap.get("values");
            String valueType = ParseFilters.getValueType(valuesString);

            fieldsFilter.setFilter(filter);
            if (valuesString.matches("\\WANY\\W")){
                List<String> resAnyList = ParseFilters.getAnyList(valueType);
                for (String item: resAnyList){
                    String clearValue = new String();
                    if (valueType == "text") {
                        clearValue = ParseFilters.unBracket(item, "\'", "\'");
                    }
                    else {
                        clearValue = item;
                    }
                    FilterValues valuesFilter = new FilterValues(valueType, clearValue);
                    fieldsFilter.addFilterValue(valuesFilter);
                }
            }
            else {
                String clearValue = new String();
                if (valueType == "text") {
                    clearValue = ParseFilters.unBracket(valuesString, "\'", "\'");
                }
                else {
                    clearValue = valuesString;
                }
                FilterValues valuesFilter = new FilterValues(valueType, clearValue);
                fieldsFilter.addFilterValue(valuesFilter);
            }
        }
        return fieldsFilter;
    }


    /**
     * Function get alias, fieid, operator and value from filters.
     *
     * @param str One of filter statment.
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
        regList.add("^\\W*(?<table>\\w+)\\W*\\.\\W*?(?<field>\\w+)\\W*(?<operator>[<>=!]+|IS)\\W(?<values>.*)$");
        regList.add("^\\W*(?<field>\\w+)\\W*?(?<operator>[<>=!]+|IS)\\W(?<values>.*)$");

        for (String s : regList) {
            Pattern pattern = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                logger.logp(
                        Level.INFO,
                        this.getClass().getCanonicalName(),
                        "parseFiltersStruct",
                        "Matcher" + matcher.toString()
                );
                result.put("field", matcher.group("field").trim());
                if (matcher.groupCount() == 4) {
                    result.put("table", matcher.group("table").trim());
                }
                ;
                result.put("operator", matcher.group("operator").trim());
                result.put("values", matcher.group("values").trim());
                logger.logp(
                        Level.INFO,
                        this.getClass().getCanonicalName(),
                        "parseFiltersStruct",
                        "Return list" + result.toString()
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
        if (Pattern.compile("::date|::time", Pattern.CASE_INSENSITIVE)
                .matcher(str)
                .find()){
           return  "tsrange";
        }
        return valueType;
    }
    public static List<String> getAnyList(String str){
        String regStr = "\\\'\\{(.*)\\}\\\'";
        Pattern pattern = Pattern.compile(regStr, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            List<String> result = Arrays.asList(matcher.group(1).split(",\\s*"));
            return result;
        }
        return null;
    }
}
