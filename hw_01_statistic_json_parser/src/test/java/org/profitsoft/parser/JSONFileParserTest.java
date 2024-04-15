package org.profitsoft.parser;

import org.junit.jupiter.api.Test;
import org.profitsoft.model.Author;
import org.profitsoft.model.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Viacheslav Korbut
 * Date: 10.04.2024
 */
class JSONFileParserTest {
    String folderPath = "src/test/resources/json_files";
    List<Book> books = JSONFileParser.parseBooksFromFolder(folderPath);


    @Test
    public void testInvalidFolderPath() {
        String invalidFolderPath = "invalid/path";
        assertThrows(RuntimeException.class, () -> JSONFileParser.parseBooksFromFolder(invalidFolderPath));
    }

    @Test
    public void testInvalidJsonFormat() {
        String filePath = "src/test/resources/json_files/test.txt";
        assertThrows(RuntimeException.class, () -> JSONFileParser.parseBooksFromFolder(filePath));
    }

    @Test
    public void testThreadPoolShutdown() {
        JSONFileParser.parseBooksFromFolder(folderPath);
        assertTrue(JSONFileParser.isExecutorShutdown());
    }

    @Test
    public void testParseMultipleBooks() {
        assertNotNull(books);
        assertTrue(books.size() > 1);
        assertEquals(3, books.size());
    }

    @Test
    void testParseSingleBook() {
        folderPath = "src/test/resources/json_single_book";
        List<Book> bookList = JSONFileParser.parseBooksFromFolder(folderPath);
        Book book = bookList.get(0);
        assertEquals("Pride and Prejudice", book.getTitle());
        assertEquals(new Author("Jane", "Austen"), book.getAuthor());
        assertEquals(1813, book.getYearPublished());
        assertEquals(1, bookList.size());
    }
}
