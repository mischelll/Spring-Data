package com.softuni.springdataintroexe.services.interfaces;

import com.softuni.springdataintroexe.domain.entities.Author;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface AuthorService {
    void seedAuthorsData() throws IOException;

    int getAuthorsCount();

    Author findAuthorById(long id);

    List<String> authorsByCountOfTheirBooks();

    List<String> getAllAuthorFNameEndsWith() throws IOException;

    List<String> getAllAuthorsByTotalBooksCopies();

    Integer getAuthorBooksCount(String fName, String lName) throws IOException;

}
