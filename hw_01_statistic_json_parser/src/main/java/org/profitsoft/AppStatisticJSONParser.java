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
    /**
     * The path to the folder containing JSON files with book data.
     */
    private static final String JSON_FILE_PATH = "hw_01_statistic_json_parser/src/main/resources/json_files";
    /**
     * The attribute based on which statistics will be calculated.
     */
    private static final String ATTRIBUTE = "year_published";

    /**
     * The main method of the application. It performs the following tasks:
     * 1. Parses JSON files containing book data from the specified folder.
     * 2. Calculates the time taken to parse the JSON files.
     * 3. Calculates statistics based on the specified attribute for the parsed book data.
     * 4. Writes the calculated statistics to an XML file.
     *
     * @param args The command-line arguments (not used).
     */
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
