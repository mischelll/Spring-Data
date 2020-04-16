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
}
