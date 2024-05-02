package ua.profitsoft.web.controller.impl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.profitsoft.util.parser.BookCreateJsonFileParser;
import ua.profitsoft.dto.create.BookCreateDTO;
import ua.profitsoft.dto.read.BookReadDTO;
import ua.profitsoft.service.BookService;
import ua.profitsoft.web.controller.BookController;
import ua.profitsoft.web.filter.BookFilterRequest;
import ua.profitsoft.web.response_dto.BookStatisticResponse;
import ua.profitsoft.writer.CSVReportGenerator;

import java.util.List;
import java.util.Map;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */

/**
 * Implementation of the controller responsible for managing books.
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookControllerImpl implements BookController {

    private final BookService bookService;

    /**
     * {@inheritDoc}
     */
    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public BookReadDTO createBook(@RequestBody @Valid BookCreateDTO bookCreateDTO) {
        return bookService.createBook(bookCreateDTO);
    }

    /**
     * {@inheritDoc}
     */
    @GetMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BookReadDTO getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);

    }

    /**
     * {@inheritDoc}
     */
    @PutMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BookReadDTO updateBookById(@PathVariable Integer id, @RequestBody @Valid BookCreateDTO bookCreateDTO) {
        return bookService.updateBook(id, bookCreateDTO);
    }

    /**
     * {@inheritDoc}
     */
    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }

    /**
     * {@inheritDoc}
     */
    @PostMapping("/book/filter_list")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookCreateDTO> bookFilterList(@RequestBody BookFilterRequest request) {
        return bookService.findAllBooksByFilter(request);
    }

    /**
     * {@inheritDoc}
     */
    @PostMapping("/book/filter_report")
    @Override
    public ResponseEntity<Resource> generateReport(@RequestBody BookFilterRequest request) {
        List<BookCreateDTO> bookCreateDTOList = bookService.findAllBooksByFilter(request).getContent();
        ByteArrayResource resource = CSVReportGenerator.generateCSVReport(bookCreateDTOList);
        String fileName = CSVReportGenerator.generateFileName();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);

    }

    /**
     * {@inheritDoc}
     */
    @PostMapping(value = "/book/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public BookStatisticResponse uploadBooks(@RequestPart("file") MultipartFile file) {
        List<BookCreateDTO> bookCreateDTOs = BookCreateJsonFileParser.parseJsonFile(file);
        return bookService.uploadBooks(bookCreateDTOs);
    }

}
