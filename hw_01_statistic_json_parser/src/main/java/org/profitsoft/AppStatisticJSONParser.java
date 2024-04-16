package org.profitsoft;

import org.profitsoft.model.Book;
import org.profitsoft.parser.JSONFileParser;
import org.profitsoft.util.StatisticsCalculator;
import org.profitsoft.writer.StatisticsXmlWriter;

import java.util.List;
import java.util.Map;


/**
 * Author: Viacheslav Korbut
 * Date: 15.04.2024
 */
public class AppStatisticJSONParser {

    private static final String JSON_FILE_PATH = "hw_01_statistic_json_parser/src/main/resources/json_files";
    private static final String ATTRIBUTE = "year_published";

    public static void main(String[] args) {
        //Fixed start current time
        long startTime = System.currentTimeMillis();

        //We take all books from json path
        List<Book> bookList = JSONFileParser.parseBooksFromFolder(JSON_FILE_PATH);

        //Fixed end time
        long endTime = System.currentTimeMillis();

        //Calculate duration parse
        long duration = endTime - startTime;
        System.out.println("Time taken: " + duration + " milliseconds with " + JSONFileParser.getNumberThreads() + " threads.");

        //We take statistic map on the attribute
        Map<String, Integer> attributeMap = StatisticsCalculator.getStatistic(ATTRIBUTE, bookList);

        //We write statistic in XML file
        StatisticsXmlWriter.writeXMLFile(attributeMap, ATTRIBUTE);
    }
}
