package ua.profitsoft.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.profitsoft.model.Author;
import ua.profitsoft.dto.read.AuthorReadDTO;
import ua.profitsoft.dto.create.AuthorCreateDTO;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 23.04.2024
 */

/**
 * Mapper interface responsible for mapping between {@link Author} entity and its corresponding DTOs.
 * Uses MapStruct library for automatic mapping implementation.
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorReadDTO toAuthorReadDTO(Author author);

    @Mapping(target = "bookList", ignore = true)
    Author toAuthor(AuthorReadDTO authorReadDTO);

    AuthorCreateDTO toAuthorCreateDTO(Author author);

    @Mapping(target = "id", ignore = true)
    Author toAuthor(AuthorCreateDTO authorCreateDTO);

    @Mapping(target = "id", ignore = true)
    AuthorReadDTO toAuthorReadDTO(AuthorCreateDTO authorCreateDTO);

    AuthorCreateDTO toAuthorCreateDTO(AuthorReadDTO authorReadDTO);

    List<AuthorCreateDTO> toListAuthorCreateDTO(List<Author> authorList);
}
