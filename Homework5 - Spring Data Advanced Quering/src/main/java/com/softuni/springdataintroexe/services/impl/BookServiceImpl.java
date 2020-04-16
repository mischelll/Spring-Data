package com.softuni.springdataintroexe.services.impl;

import com.softuni.springdataintroexe.domain.entities.*;
import com.softuni.springdataintroexe.repositories.BookRepository;
import com.softuni.springdataintroexe.services.interfaces.AuthorService;
import com.softuni.springdataintroexe.services.interfaces.BookService;
import com.softuni.springdataintroexe.services.interfaces.CategoryService;
import com.softuni.springdataintroexe.utils.FileReader;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private final BufferedReader bufferedReader;

    public BookServiceImpl(AuthorService authorService, CategoryService categoryService, BookRepository bookRepository, FileReader fileReader) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookRepository = bookRepository;
        this.fileReader = fileReader;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
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
        return this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleDesc("George", "Powell");
    }

    @Override
    public List<String> getAllBooksTitlesByAgeRestriction() throws IOException {
        System.out.println("Enter age restriction type(MINOR, TEEN, ADULT): ");
        String ageRestriction = bufferedReader.readLine().toLowerCase();
        List<Book> allByAgeRestriction = null;
        switch (ageRestriction) {
            case "minor":
                allByAgeRestriction = this.bookRepository.findAllByAgeRestriction(AgeRestriction.MINOR);
                break;
            case "teen":
                allByAgeRestriction = this.bookRepository.findAllByAgeRestriction(AgeRestriction.TEEN);
                break;
            case "adult":
                allByAgeRestriction = this.bookRepository.findAllByAgeRestriction(AgeRestriction.ADULT);
                break;
            default:
                System.out.println("No such age restriction type!");
                break;
        }
        List<String> books = allByAgeRestriction
                .stream()
                .map(book -> String.format("%s%n", book.getTitle()))
                .collect(Collectors.toList());

        return allByAgeRestriction != null ? allByAgeRestriction
                .stream()
                .map(book -> String.format("%s%n", book.getTitle()))
                .collect(Collectors.toList())
                : null;
    }

    @Override
    public List<String> getAllBooksTitlesByEditionTypeCopiesLessThan() {
        final EditionType editionType = EditionType.GOLD;
        final Integer copies = 5000;
        List<Book> allByEditionTypeAndCopiesIsLessThan = this.bookRepository
                .findAllByEditionTypeAndCopiesIsLessThan(editionType, copies);

        return allByEditionTypeAndCopiesIsLessThan
                .stream()
                .map(book -> String.format("%s%n", book.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksByPriceLessThanAndGreaterThan() {
        final BigDecimal priceLess = BigDecimal.valueOf(5);
        final BigDecimal priceGreater = BigDecimal.valueOf(40);
        List<Book> allByPriceIsLessThanAndPriceGreaterThan = this.bookRepository.findAllByPriceIsLessThanOrPriceGreaterThan(priceLess, priceGreater);

        return allByPriceIsLessThanAndPriceGreaterThan
                .stream()
                .map(book -> String.format("%s - $%.2f%n", book.getTitle(), book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksNotReleasedInYear() throws IOException {
        System.out.println("Enter year: ");
        int year = Integer.parseInt(bufferedReader.readLine());
        LocalDate before = LocalDate.of(year, 1, 1);
        LocalDate after = LocalDate.of(year, 12, 31);

        List<Book> allByReleaseDateBeforeOrReleaseDateAfter = this.bookRepository
                .findAllByReleaseDateBeforeOrReleaseDateAfter(before, after);
        return allByReleaseDateBeforeOrReleaseDateAfter
                .stream()
                .map(book -> String.format("%s%n", book.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksBeforeGivenReleaseDate() throws IOException {
        System.out.println("Enter release date(dd-MM-yyyy): ");
        String inputDate = bufferedReader.readLine();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(inputDate, dateTimeFormatter);
        List<Book> allByReleaseDateBefore = this.bookRepository.findAllByReleaseDateBefore(date);

        return allByReleaseDateBefore
                .stream()
                .map(book -> String.format("%s %s %.2f%n",
                        book.getTitle(),
                        book.getEditionType(),
                        book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksTitleContains() throws IOException {
        System.out.println("Enter pattern: ");
        String pattern = bufferedReader.readLine().toLowerCase();

        List<Book> allByTitleEndsWith = this.bookRepository.findAllByTitleContains(pattern);

        return allByTitleEndsWith
                .stream()
                .map(book -> String.format("%s%n", book.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksByAuthorLastNamePattern() throws IOException {
        System.out.println("Enter pattern: ");
        String pattern = bufferedReader.readLine().toLowerCase();

        List<Book> allByAuthorLastNameStartsWith = this.bookRepository.findAllByAuthorLastNameStartsWith(pattern);

        return allByAuthorLastNameStartsWith
                .stream()
                .map(book -> String.format("%s (%s %s)%n"
                        , book.getTitle()
                        , book.getAuthor().getFirstName()
                        , book.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getAllBooksCountByTitleLength() throws IOException {
        System.out.println("Enter length: ");
        int length = Integer.parseInt(bufferedReader.readLine());

        List<Book> allByTitleLength = this.bookRepository.findAllByTitleLength(length);

        return allByTitleLength.size();
    }

    @Override
    public List<String> getAllBooksByTitle() throws IOException {
        System.out.println("Enter book title: ");
        String title = bufferedReader.readLine();

        return this.bookRepository
                .findAllByTitle(title)
                .stream()
                .map(book -> String.format("%s %s %s %.2f%n"
                        , book.getTitle()
                        , book.getEditionType()
                        , book.getAgeRestriction()
                        , book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer increaseBookCopiesCount() throws IOException {
        System.out.println("Enter date(dd MMM yyyy): ");
        String date = bufferedReader.readLine().replace(" ","/");
        System.out.println("Enter number of book copies: ");
        int copies = Integer.parseInt(bufferedReader.readLine());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
        List<Book> allByReleaseDateAfter = this.bookRepository.findAllByReleaseDateAfter(localDate);
        this.bookRepository.increaseBookCopiesWithReleaseDateAfter(copies,localDate);

        return allByReleaseDateAfter.size() * copies;
    }

    @Override
    public Integer getDeletedBooks() throws IOException {
        System.out.println("Enter copies number: ");
        Integer number = Integer.parseInt(bufferedReader.readLine());


        return this.bookRepository.deleteBookByCopiesIsLessThan(number);
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
