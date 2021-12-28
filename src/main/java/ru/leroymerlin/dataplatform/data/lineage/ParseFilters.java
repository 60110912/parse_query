package ru.leroymerlin.dataplatform.data.lineage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
     * Медод удалает лишние скобки в начале и в конце.
     *
     * @param str входная строка
     */
    public static String unBracket(String str) {

        Pattern pattern = Pattern.compile("^\\((.*)\\)$");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            return matcher.group(1);
        }
        return str;
    }

    public HashMap<String, String> getFiltersValue() {
        List<String> filters = this.splitFilters();
        HashMap<String, String> result = new HashMap<>();
        for (String s : filters) {
            HashMap<String, String> valuesMap = parseFiltersStruct(s);
            logger.logp(
                    Level.INFO,
                    this.getClass().getCanonicalName(),
                    "unBracket",
                    "This add to filter list" + valuesMap.toString()
            );
        }
        return null;
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
            Pattern pattern = Pattern.compile(s);
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
}
