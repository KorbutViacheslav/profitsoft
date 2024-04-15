package org.profitsoft.writer;

import org.profitsoft.util.StatisticsFileNameGenerator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * EN: Utility class for writing statistics data to XML files.
 * This class provides a convenient way to generate XML files with statistics data.
 * It contains a method for writing statistics data in XML format based on the provided map of statistics.
 * Author: Viacheslav Korbut
 * Date: 04.04.2024
 */
public class StatisticsXmlWriter {
    /**
     * EN: Private constructor to prevent instantiation of the XmlWriter class.
     */
    private StatisticsXmlWriter() {

    }

    /**
     * EN: Writes statistics data to an XML file.
     *
     * @param statisticMap The map containing statistics data.
     * @param fileName     The name of the XML file.
     * @throws RuntimeException if an error occurs while writing the XML file.
     */
    public static void writeXMLFile(Map<String, Integer> statisticMap, String fileName) {

        try (var bufferedWriter = createBufferedWriter(fileName)) {
            bufferedWriter.write("<statistics>\n");
            for (Map.Entry<String, Integer> entry : statisticMap.entrySet()) {
                String attribute = entry.getKey();
                Integer count = entry.getValue();
                bufferedWriter.write("  <item>\n");
                bufferedWriter.write("    <value>" + attribute + "</value>\n");
                bufferedWriter.write("    <count>" + count + "</count>\n");
                bufferedWriter.write("  </item>\n");
            }
            bufferedWriter.write("</statistics>");
        } catch (IOException e) {
            System.err.println("An error occurred while writing the XML file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * EN: Creates a buffered writer for the specified XML file.
     *
     * @param fileName The name of the XML file.
     * @return A BufferedWriter object for writing to the XML file.
     * @throws IOException if an I/O error occurs when opening or creating the file.
     */
    private static BufferedWriter createBufferedWriter(String fileName) throws IOException {
        String filePath = StatisticsFileNameGenerator.getXMLFileName(fileName);
        return Files.newBufferedWriter(Path.of(filePath));
    }
}
