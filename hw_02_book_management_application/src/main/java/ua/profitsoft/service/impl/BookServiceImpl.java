package ua.profitsoft.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ua.profitsoft.repository.AuthorRepository;
import ua.profitsoft.dto.create.AuthorCreateDTO;
import ua.profitsoft.dto.create.BookCreateDTO;
import ua.profitsoft.dto.read.BookReadDTO;
import ua.profitsoft.model.Author;
import ua.profitsoft.model.Book;
import ua.profitsoft.repository.BookRepository;
import ua.profitsoft.service.AuthorService;
import ua.profitsoft.service.BookService;
import ua.profitsoft.util.exeption.error.ResourceIsExistException;
import ua.profitsoft.util.exeption.error.ResourceNotFoundException;
import ua.profitsoft.mapper.AuthorMapper;
import ua.profitsoft.mapper.BookMapper;
import ua.profitsoft.web.filter.BookFilterRequest;
import ua.profitsoft.web.filter.BookSpecification;
import ua.profitsoft.web.response_dto.BookStatisticResponse;

import java.util.*;

/**
 * Author: Viacheslav Korbut
 * Date: 18.04.2024
 */
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorService authorService;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final Validator validator;

    /**
     * {@inheritDoc}
     */
    @Override
    public BookReadDTO createBook(BookCreateDTO bookCreateDTO) {
        Author author = getOrCreateAuthor(bookCreateDTO.getAuthor());
        Book book = bookMapper.toBook(bookCreateDTO);
        book.setAuthor(author);
        Book savedBook = saveBook(book);
        return bookMapper.toBookReadDTO(savedBook);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookReadDTO getBookById(Integer id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBookReadDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookReadDTO updateBook(Integer id, BookCreateDTO bookCreateDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        Author author = getOrCreateAuthor(bookCreateDTO.getAuthor());
        existingBook.setAuthor(author);
        existingBook.setTitle(bookCreateDTO.getTitle());
        existingBook.setYearPublished(bookCreateDTO.getYearPublished());
        existingBook.setGenres(new HashSet<>(bookCreateDTO.getGenres()));

        Book updatedBook = saveBook(existingBook);
        return bookMapper.toBookReadDTO(updatedBook);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteBookById(Integer id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<BookCreateDTO> findAllBooksByFilter(BookFilterRequest bookFilterRequest) {
        Specification<Book> specification = new BookSpecification(bookFilterRequest);
        Pageable pageable = PageRequest.of(bookFilterRequest.getPage(), bookFilterRequest.getSize());
        Page<Book> bookPage = bookRepository.findAll(specification, pageable);
        return bookPage.map(bookMapper::toBookCreateDTO);
    }

    public List<BookCreateDTO> findAllBookByFilterWithoutPagination(BookFilterRequest bookFilterRequest){
        Specification<Book> specification = new BookSpecification(bookFilterRequest);
        List<Book> list = bookRepository.findAll(specification);
        List<BookCreateDTO> b = list.stream().map(bookMapper::toBookCreateDTO).toList();
        return b;
    }

    /**
     * {@inheritDoc}
     */
    public BookStatisticResponse uploadBooks(List<BookCreateDTO> bookCreateDTOs) {
        int successfullyImported = 0;
        int failedImports = 0;
        List<String> failureReasons = new ArrayList<>();

        for (BookCreateDTO bookCreateDTO : bookCreateDTOs) {
            try {
                if (!isBookCreateDTOValid(bookCreateDTO, failureReasons)) {
                    failedImports++;
                    continue;
                }

                Author author = getAuthor(bookCreateDTO, failureReasons);
                if (author == null) {
                    failedImports++;
                    continue;
                }

                Book book = bookMapper.toBook(bookCreateDTO);
                book.setAuthor(author);

                if (isBookDuplicate(book, failureReasons)) {
                    failedImports++;
                    continue;
                }

                bookRepository.save(book);
                successfullyImported++;
            } catch (Exception e) {
                String errorMessage = e.getMessage();
                failureReasons.add("Failed to import book: " + bookCreateDTO.getTitle() + ". Reason: " + errorMessage);
                failedImports++;
            }
        }

        return new BookStatisticResponse(successfullyImported, failedImports, failureReasons);
    }

    @Override
    public List<BookReadDTO> findAllBooks() {
        List<Book> list = bookRepository.findAll();
        List<BookReadDTO> lr = bookMapper.toBookReadDTOList(list);
        return lr;
    }

    /**
     * Validates the provided {@link BookCreateDTO} object.
     * This method is used in {@link #uploadBooks(List)}.
     *
     * @param bookCreateDTO  The {@link BookCreateDTO} object to validate.
     * @param failedToImport A list to collect error messages if validation fails.
     * @return True if the {@link BookCreateDTO} is valid, otherwise false.
     */
    private boolean isBookCreateDTOValid(BookCreateDTO bookCreateDTO, List<String> failedToImport) {
        Set<ConstraintViolation<BookCreateDTO>> violations = validator.validate(bookCreateDTO);
        if (!violations.isEmpty()) {
            StringBuilder violationMessages = new StringBuilder();
            for (ConstraintViolation<BookCreateDTO> violation : violations) {
                violationMessages.append(violation.getMessage()).append("; ");
            }
            failedToImport.add("Failed to import book: " + bookCreateDTO.getTitle() + ". Reason: " + violationMessages);
            return false;
        }
        return true;
    }

    /**
     * Retrieves an existing author from the database or creates a new one if not found.
     * This method is used in {@link #uploadBooks(List)} and {@link #createBook(BookCreateDTO)}.
     *
     * @param bookCreateDTO  The {@link BookCreateDTO} containing author information.
     * @param failedToImport A list to collect error messages if author retrieval fails.
     * @return The {@link Author} object found or created.
     */
    private Author getAuthor(BookCreateDTO bookCreateDTO, List<String> failedToImport) {
        Author author = authorMapper.toAuthor(bookCreateDTO.getAuthor());
        Optional<Author> existingAuthor = authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName());
        if (existingAuthor.isEmpty()) {
            failedToImport.add("Failed to import book: " + bookCreateDTO.getTitle() + ". Reason: Author not found in the database.");
            return null;
        }
        return existingAuthor.get();
    }

    /**
     * Checks if a book with the same title and author already exists in the database.
     * This method is used in {@link #uploadBooks(List)}.
     *
     * @param book           The {@link Book} object to check for duplicates.
     * @param failedToImport A list to collect error messages if duplicate checking fails.
     * @return True if a duplicate book is found, otherwise false.
     */
    private boolean isBookDuplicate(Book book, List<String> failedToImport) {
        if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            failedToImport.add("Failed to import book: " + book.getTitle() + ". Reason: Duplicate book entry in the database.");
            return true;
        }
        return false;
    }

    /**
     * Retrieves an existing author from the database or creates a new one if not found.
     * This method is used in {@link #createBook(BookCreateDTO)}.
     *
     * @param authorCreateDTO The {@link AuthorCreateDTO} object containing author information.
     * @return The {@link Author} object found or created.
     * @throws ResourceIsExistException If the author already exists in the database.
     */
    private Author getOrCreateAuthor(AuthorCreateDTO authorCreateDTO) {
        try {
            return authorService.createAuthor(authorMapper.toAuthor(authorCreateDTO));
        } catch (ResourceIsExistException ex) {
            return authorService.findByFirstNameAndLastName(
                    authorCreateDTO.getFirstName(),
                    authorCreateDTO.getLastName());
        }
    }

    /**
     * Saves the provided book to the database.
     * This method is used in {@link #createBook(BookCreateDTO)} and {@link #updateBook(Integer, BookCreateDTO)}.
     *
     * @param book The {@link Book} object to save.
     * @return The saved {@link Book} object.
     * @throws ResourceIsExistException If the book already exists in the database.
     */
    private Book saveBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceIsExistException();
        }
    }

}
