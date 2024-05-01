package ua.profitsoft.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.profitsoft.repository.AuthorRepository;
import ua.profitsoft.model.Author;
import ua.profitsoft.service.AuthorService;
import ua.profitsoft.util.exeption.error.ResourceIsExistException;
import ua.profitsoft.util.exeption.error.ResourceNotFoundException;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 20.04.2024
 */
@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Author createAuthor(Author author) {
        try {
            return authorRepository.save(author);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceIsExistException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Author updateAuthor(Integer id, Author author) {
        Author a = getAuthorById(id);
        a.setFirstName(author.getFirstName());
        a.setLastName(author.getLastName());
        return authorRepository.save(a);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAuthorById(Integer id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Author findByFirstNameAndLastName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
