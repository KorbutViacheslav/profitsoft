package org.profitsoft.parser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.profitsoft.model.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: Viacheslav Korbut
 * Date: 04.04.2024
 */

/**
 * Utility class for parsing JSON files containing information about books.
 * Provides method for asynchronously parsing JSON files in a specified folder.
 */
public class JSONFileParser {
    /**
     * The number of threads used for parallel parsing of JSON files.
     */
    private static final int NUM_THREADS = 4;

    private static ExecutorService executor;

    /**
     * Private constructor to prevent instantiation of the JSONFileParser class.
     * This class is designed as a utility class with only static methods.
     */
    private JSONFileParser() {
    }

    /**
     * Parses all JSON files containing information about books in the specified folder asynchronously.
     *
     * @param folderPath The path to the folder containing JSON files.
     * @return A list of books parsed from the JSON files.
     * @throws RuntimeException if an error occurs while reading the folder or parsing the files.
     */
    public static List<Book> parseBooksFromFolder(String folderPath) {
        executor = Executors.newFixedThreadPool(NUM_THREADS);

        try (var pathStream = Files.list(Path.of(folderPath))) {
            return pathStream
                    .filter(JSONFileParser::isJSONFile)
                    .map(path -> JSONFileParser.parseJsonFilesAsync(path, executor))
                    .map(CompletableFuture::join)
                    .flatMap(List::stream)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read folder: " + folderPath, e);
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Asynchronously parses a JSON file containing information about books.
     *
     * @param path     The path to the JSON file.
     * @param executor The executor object to use for asynchronous execution.
     * @return A CompletableFuture representing the parsing operation.
     */
    private static CompletableFuture<List<Book>> parseJsonFilesAsync(Path path, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> parseBookFromFile(path), executor);
    }

    /**
     * Parses a JSON file containing information about books synchronously.
     *
     * @param path The path to the JSON file.
     * @return A list of books parsed from the JSON file.
     */
    private static List<Book> parseBookFromFile(Path path) {
        Gson gson = new Gson();

        try (var br = Files.newBufferedReader(path)) {
            return gson.fromJson(br, new TypeToken<List<Book>>() {
            }.getType());
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Failed to parse file: " + path);
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Checks whether the given path represents a JSON file based on its extension.
     *
     * @param path The path to check.
     * @return {@code true} if the file represented by the path has a ".json" extension, {@code false} otherwise.
     */
    private static boolean isJSONFile(Path path) {
        return path.getFileName().toString().endsWith(".json");
    }

    /**
     * Checks whether the executor service used for asynchronous parsing of JSON files
     * has been shutdown or not.
     *
     * @return {@code true} if the executor is either null or has been shutdown, {@code false} otherwise.
     */
    public static boolean isExecutorShutdown() {
        return executor == null || executor.isShutdown();
    }

    /**
     * Retrieves the number of threads used for parallel parsing of JSON files.
     *
     * @return The number of threads used for parallel parsing.
     */
    public static int getNumberThreads() {
        return NUM_THREADS;
    }
}

