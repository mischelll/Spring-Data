package com.softuni.springdataintroexe.services.interfaces;

import com.softuni.springdataintroexe.domain.entities.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface BookService {
    void seedBooksData() throws IOException;

    List<Book> getAllBooksAfterCertainYear(int year);

    List<String> findAllAuthorsBeforeCertainYear(int year);

    List<Book> getAllBooksByAuthor();

    List<String> getAllBooksTitlesByAgeRestriction() throws IOException;

    List<String> getAllBooksTitlesByEditionTypeCopiesLessThan();

    List<String> getAllBooksByPriceLessThanAndGreaterThan();

    List<String> getAllBooksNotReleasedInYear() throws IOException;

    List<String> getAllBooksBeforeGivenReleaseDate() throws IOException;

    List<String> getAllBooksTitleContains() throws IOException;

    List<String> getAllBooksByAuthorLastNamePattern() throws IOException;

    Integer getAllBooksCountByTitleLength() throws IOException;

    List<String> getAllBooksByTitle() throws IOException;

    Integer increaseBookCopiesCount() throws IOException;

    Integer getDeletedBooks() throws IOException;


}
