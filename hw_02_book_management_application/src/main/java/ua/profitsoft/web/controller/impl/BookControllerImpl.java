package ua.profitsoft.web.controller.impl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.profitsoft.messaging.BookReceivedMessage;
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
import java.util.stream.Collectors;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */

/**
 * Implementation of the controller responsible for managing books.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/library", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class BookControllerImpl implements BookController {

    private final BookService bookService;

    private final KafkaOperations<String, BookReceivedMessage> kafkaOperations;

    @Value("${kafka.topic.bookReceived}")
    private String bookReceivedTopic;

    /**
     * {@inheritDoc}
     */
    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public BookReadDTO createBook(@RequestBody @Valid BookCreateDTO bookCreateDTO) {
        BookReadDTO bookReadDTO = bookService.createBook(bookCreateDTO);
        BookReceivedMessage message = toMessage(bookCreateDTO);
        kafkaOperations.send(bookReceivedTopic, message);
        return bookReadDTO;
    }

    private static BookReceivedMessage toMessage(BookCreateDTO bookCreateDTO) {
        return BookReceivedMessage.builder()
                .name(bookCreateDTO.getTitle())
                .genre(bookCreateDTO.getGenres()
                        .stream()
                        .filter(genre->!genre.isEmpty())
                        .collect(Collectors.joining(", ")))
                .yearPublished(bookCreateDTO.getYearPublished())
                .authorFirstName(bookCreateDTO.getAuthor().firstName)
                .authorLastName(bookCreateDTO.getAuthor().lastName)
                .build();
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

    @PostMapping("/book/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<BookCreateDTO> bookFilter(@RequestBody BookFilterRequest request) {
        return bookService.findAllBookByFilterWithoutPagination(request);
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
    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List<BookReadDTO> getAllBooks(){
        return bookService.findAllBooks();
    }

}
