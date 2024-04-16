package org.profitsoft.util;

import org.profitsoft.model.Book;
import org.profitsoft.model.Genre;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: Viacheslav Korbut
 * Date: 04.04.2024
 */

/**
 * Utility class for calculating statistics based on a list of books.
 * This class provides methods to calculate statistics such as the number of books by title, author, year published, or genre.
 */
public class StatisticsCalculator {

    private StatisticsCalculator() {
    }

    /**
     * Calculates statistics based on the specified attribute and list of books.
     *
     * @param attribute The attribute to calculate statistics for (e.g., "title", "author", "yearPublished", "genre").
     * @param bookList  The list of books to calculate statistics from.
     * @return A map containing the statistics for the specified attribute.
     * The keys represent attribute values, and the values represent the number of occurrences of each attribute value.
     * @throws IllegalArgumentException if the attribute is invalid.
     */
    public static Map<String, Integer> getStatistic(String attribute, List<Book> bookList) {
        Map<String, Integer> statistics = new HashMap<>();

        for (Book book : bookList) {
            switch (attribute) {
                case "title" -> statistics.compute(book.getTitle(), (key, value) -> value == null ? 1 : value + 1);
                case "yearPublished", "year_published" ->
                        statistics.compute(book.getYearPublished().toString(), (key, value) -> value == null ? 1 : value + 1);
                case "author" ->
                        statistics.compute(book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName(), (key, value) -> value == null ? 1 : value + 1);
                case "genre" -> {
                    if (book.getGenre() != null) {
                        for (Genre genre : book.getGenre()) {
                            if (genre != null) {
                                statistics.compute(genre.name(), (key, value) -> value == null ? 1 : value + 1);
                            }
                        }
                    }
                }
                default -> throw new IllegalArgumentException("Invalid attribute - " + attribute);
            }
        }

        return statistics.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

    }
}
