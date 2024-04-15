package org.profitsoft.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Author: Viacheslav Korbut
 * Date: 10.04.2024
 */
public class StatisticsFileNameGeneratorTest {

    @Test
    void testGetXMLFileName() {
        String actual = StatisticsFileNameGenerator.getXMLFileName("author");
        String expected = "src/main/resources/statistic_results/statistics_by_author.xml";
        assertEquals(expected, actual);
    }

    @Test
    void testGetXMLFileNameIfEmptyAttribute() {
        String expected = "src/main/resources/statistic_results/statistics_by_.xml";
        String actual = StatisticsFileNameGenerator.getXMLFileName("");
        assertEquals(expected, actual);
    }
}
