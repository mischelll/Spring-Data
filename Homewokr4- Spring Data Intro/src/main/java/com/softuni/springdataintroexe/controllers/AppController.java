package com.softuni.springdataintroexe.controllers;

import com.softuni.springdataintroexe.domain.entities.Book;
import com.softuni.springdataintroexe.services.interfaces.AuthorService;
import com.softuni.springdataintroexe.services.interfaces.BookService;
import com.softuni.springdataintroexe.services.interfaces.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.categoryService.seedCategories();
//        this.authorService.seedAuthorsData();
//         this.bookService.seedBooksData();
//
//        List<Book> allBooksAfterCertainYear = this.bookService.getAllBooksAfterCertainYear(2000);
//        printAllBooks(allBooksAfterCertainYear);
//
//        List<String> authorsWithBooksBefore = this.bookService.findAllAuthorsBeforeCertainYear(1990);
//         printAllAuthors(authorsWithBooksBefore);

//        List<String> authorsByBooksCount = this.authorService.authorsByCountOfTheirBooks();
//        printAllAuthors(authorsByBooksCount);

        printBooksByAuthor();
    }

    private void printBooksByAuthor() {
        this.bookService.getAllBooksByAuthor().forEach(book -> {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            String formattedDate = book.getReleaseDate().format(dateTimeFormatter);
            System.out.printf("Title: %s Release Date: %s Number of copies: %d%n", book.getTitle(),formattedDate,book.getCopies());
        });
    }

    private void printAllAuthors(List<String> authors) {
        authors.forEach(System.out::print);
    }

    private void printAllBooks(List<Book> allBooksAfterCertainYear) {
        allBooksAfterCertainYear.forEach(book -> System.out.printf("%s%n", book.getTitle()));
    }
}
