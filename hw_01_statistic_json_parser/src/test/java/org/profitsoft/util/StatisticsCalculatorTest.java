package org.profitsoft.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.profitsoft.model.Author;
import org.profitsoft.model.Book;
import org.profitsoft.model.Genre;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Author: Viacheslav Korbut
 * Date: 10.04.2024
 */
class StatisticsCalculatorTest {
    private List<Book> bookList;

    @BeforeEach
    void setUp() {
        bookList = Arrays.asList(
                new Book("A Song of Ice and Fire: A Game of Thrones", new Author("George", "Martin"), 1996, Arrays.asList(Genre.FANTASY, Genre.EPIC)),
                new Book("A Song of Ice and Fire: A Clash of Kings", new Author("George", "Martin"), 1998, Arrays.asList(Genre.FANTASY, Genre.EPIC)),
                new Book("The Shining", new Author("Stephen", "King"), 1977, Arrays.asList(Genre.HORROR, Genre.PSYCHOLOGICAL)),
                new Book("It", new Author("Stephen", "King"), 1986, Arrays.asList(Genre.HORROR, Genre.THRILLER)),
                new Book("Harry Potter and the Philosopher's Stone", new Author("J.K.", "Rowling"), 1997, Arrays.asList(Genre.FANTASY, Genre.FOR_CHILDREN)),
                new Book("1984", new Author("George", "Orwell"), 1949, Arrays.asList(Genre.DYSTOPIAN, Genre.POLITICAL_FICTION)),
                new Book("Pride and Prejudice", new Author("Jane", "Austen"), 1813, Arrays.asList(Genre.ROMANCE, Genre.SATIRE)),
                new Book("Romeo and Juliet", new Author("William", "Shakespeare"), 1597, Arrays.asList(Genre.ROMANCE, Genre.TRAGEDY))
        );
    }

    @Test
    void testGetStatisticByTitle() {
        var statistics = StatisticsCalculator.getStatistic("title", bookList);
        assertEquals(8, statistics.size());
        assertEquals(1, statistics.get("Harry Potter and the Philosopher's Stone"));
    }

    @Test
    void testGetStatisticByYearPublished() {
        var statistics = StatisticsCalculator.getStatistic("yearPublished", bookList);
        assertEquals(8, statistics.size());
        assertEquals(1, statistics.get("1997"));
    }

    @Test
    void testGetStatisticByAuthor() {
        var statistics = StatisticsCalculator.getStatistic("author", bookList);
        assertEquals(6, statistics.size());
        assertEquals(1, statistics.get("J.K. Rowling"));
    }

    @Test
    void testGetStatisticByGenre() {
        var statistics = StatisticsCalculator.getStatistic("genre", bookList);
        assertEquals(11, statistics.size());
        assertEquals(3, statistics.get("FANTASY"));
    }

    @Test
    void testInvalidAttribute() {
        assertThrows(IllegalArgumentException.class, () -> {
            StatisticsCalculator.getStatistic("Invalid attribute", bookList);
        });
    }

}