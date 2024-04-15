package org.profitsoft.writer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.profitsoft.util.StatisticsFileNameGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Author: Viacheslav Korbut
 * Date: 10.04.2024
 */
class StatisticsXmlWriterTest {
    private Map<String, Integer> statisticMap;
    private String fileName;
    private Path filePath;

    @BeforeEach
    void setUp() {
        statisticMap = new HashMap<>();
        statisticMap.put("J.K. Rowling", 3);
        statisticMap.put("Stephen King", 2);
        statisticMap.put("George Martin", 2);
        fileName = "author";

        StatisticsXmlWriter.writeXMLFile(statisticMap, fileName);
        filePath = Path.of(StatisticsFileNameGenerator.getXMLFileName(fileName));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(filePath);
    }

    @Test
    void testWriteXMLFile() throws IOException {
        assertTrue(Files.exists(filePath), "XML file should be created");
    }

    @Test
    void testWriteXMLFileShoutBeNotEmpty() throws IOException {
        assertTrue(Files.size(filePath) > 0, "XML file should not be empty");
    }

    @Test
    void testWriteXMLFileContainsExpectedContent() throws IOException {
        try (BufferedReader bufferedWriter = Files.newBufferedReader(filePath)) {
            String expectedContent = bufferedWriter.lines().collect(Collectors.joining("\n"));

            assertTrue(expectedContent.contains("<statistics>"), "XML file should contain <statistics> tag");
            assertTrue(expectedContent.contains("<item>"), "XML file should contain <item> tag");
            assertTrue(expectedContent.contains("<value>J.K. Rowling</value>"), "XML file should contain <value>J.K. Rowling</value> tag");
        }
    }
}