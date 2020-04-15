package com.softuni.springdataintroexe.services.impl;

import com.softuni.springdataintroexe.domain.entities.*;
import com.softuni.springdataintroexe.repositories.BookRepository;
import com.softuni.springdataintroexe.services.interfaces.AuthorService;
import com.softuni.springdataintroexe.services.interfaces.BookService;
import com.softuni.springdataintroexe.services.interfaces.CategoryService;
import com.softuni.springdataintroexe.utils.FileReader;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.softuni.springdataintroexe.constants.GlobalConstants.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookRepository bookRepository;
    private final FileReader fileReader;

    public BookServiceImpl(AuthorService authorService, CategoryService categoryService, BookRepository bookRepository, FileReader fileReader) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookRepository = bookRepository;
        this.fileReader = fileReader;
    }

    @Override
    public void seedBooksData() throws IOException {
        String[] fileContent = this.fileReader.readFileContent(BOOKS_FILE_PATH);
        if (this.bookRepository.count() != 0) {

        } else {
            Arrays.stream(fileContent)
                    .forEach(r -> {
                        String[] parameters = r.split("\\s+");
                        Author randomAuthor = getRandomAuthor();
                        EditionType editionType = EditionType.values()[Integer.parseInt(parameters[0])];
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                        LocalDate date = LocalDate.parse(parameters[1], dateTimeFormatter);
                        int numberOfCopies = Integer.parseInt(parameters[2]);
                        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(parameters[3]));
                        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(parameters[4])];
                        String title = this.getTitle(parameters);
                        Set<Category> categories = this.getRandomCategories();

                        Book book = new Book();
                        book.setAuthor(randomAuthor);
                        book.setAgeRestriction(ageRestriction);
                        book.setCategories(categories);
                        book.setCopies(numberOfCopies);
                        book.setEditionType(editionType);
                        book.setPrice(price);
                        book.setReleaseDate(date);
                        book.setTitle(title);

                        this.bookRepository.saveAndFlush(book);
                        System.out.println();
                    });
        }
    }

    @Override
    public List<Book> getAllBooksAfterCertainYear(int year) {
        LocalDate date = LocalDate.of(year, 12, 31);
        List<Book> books = this.bookRepository.findAllByReleaseDateAfter(date);

        return books;
    }

    @Override
    public List<String> findAllAuthorsBeforeCertainYear(int year) {
        LocalDate localDate = LocalDate.of(1990, 1, 1);

        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(localDate);
        return books
                .stream()
                .map(book -> String.format("%s %s%n", book.getAuthor().getFirstName()
                        , book.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllBooksByAuthor() {
        return this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleDesc("George","Powell");
    }


    private Set<Category> getRandomCategories() {
        Set<Category> categoriesResult = new HashSet<Category>();
        Random random = new Random();
        int count = random.nextInt(2) + 1;

        for (int i = 1; i <= count; i++) {
            categoriesResult.add(this.categoryService.getCategoryById((long) i));
        }

        return categoriesResult;
    }


    private String getTitle(String[] parameters) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 5; i < parameters.length; i++) {
            stringBuilder.append(parameters[i]).append(" ");
        }
        return stringBuilder.toString().trim();
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        int randomAuthorId = random.nextInt(this.authorService.getAuthorsCount()) + 1;

        return this.authorService.findAuthorById((long) randomAuthorId);
    }
}
