package ua.profitsoft.web.controller.impl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.profitsoft.model.Author;
import ua.profitsoft.dto.create.AuthorCreateDTO;
import ua.profitsoft.dto.read.AuthorReadDTO;
import ua.profitsoft.service.AuthorService;
import ua.profitsoft.mapper.AuthorMapper;
import ua.profitsoft.web.controller.AuthorController;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 23.04.2024
 */

/**
 * Implementation of the controller responsible for managing authors.
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/library", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class AuthorControllerImpl implements AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    /**
     * {@inheritDoc}
     */
    @PostMapping("/author")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public AuthorReadDTO createAuthor(@RequestBody @Valid AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.toAuthor(authorCreateDTO);
        authorService.createAuthor(author);
        return authorMapper.toAuthorReadDTO(author);
    }

    /**
     * {@inheritDoc}
     */
    @GetMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public AuthorReadDTO getAuthorById(@PathVariable Integer id) {
        Author author = authorService.getAuthorById(id);
        return authorMapper.toAuthorReadDTO(author);
    }

    /**
     * {@inheritDoc}
     */
    @PutMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public AuthorReadDTO updateAuthorById(@PathVariable Integer id, @RequestBody @Valid AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.toAuthor(authorCreateDTO);
        return authorMapper.toAuthorReadDTO(authorService.updateAuthor(id, author));
    }

    /**
     * {@inheritDoc}
     */
    @DeleteMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteAuthorById(@PathVariable Integer id) {
        authorService.deleteAuthorById(id);
    }

    /**
     * {@inheritDoc}
     */
    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<AuthorReadDTO> getAllAuthors() {
        return authorMapper.toListAuthorReadDTO(authorService.getAllAuthor());
        //return authorMapper.toListAuthorCreateDTO(authorService.getAllAuthor());
    }
}
