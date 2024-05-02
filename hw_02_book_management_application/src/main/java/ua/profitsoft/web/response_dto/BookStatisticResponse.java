package ua.profitsoft.web.response_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 02.05.2024
 */

/**
 * Represents a response containing statistics about the uploaded books.
 *
 * @successfullyImported: The number of books successfully imported.
 * @failedImports: The number of failed imports.
 * @failureReasons: A list of failure reasons for each book that failed to import.
 * Each failure reason is represented as a string.
 */
@Getter
@Setter
@AllArgsConstructor
public class BookStatisticResponse {
    private int successfullyImported;
    private int failedImports;
    private List<String> failureReasons;
}
