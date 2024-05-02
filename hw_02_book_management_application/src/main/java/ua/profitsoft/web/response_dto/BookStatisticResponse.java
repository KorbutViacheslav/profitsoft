package ua.profitsoft.web.response_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 02.05.2024
 */
@Getter
@Setter
@AllArgsConstructor
public class BookStatisticResponse {
    private int successfullyImported;
    private int failedImports;
    private List<String> failureReasons;
}
