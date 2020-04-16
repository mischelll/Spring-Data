package com.softuni.springdataintroexe.controllers;


import com.softuni.springdataintroexe.services.interfaces.AuthorService;
import com.softuni.springdataintroexe.services.interfaces.BookService;
import com.softuni.springdataintroexe.services.interfaces.CategoryService;
import com.softuni.springdataintroexe.utils.Messages;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;

@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private BufferedReader bufferedReader;

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

//        printBooksByAuthor();


        //BEFORE THE HOMEWORK:
            //1.CHECK THE RESOURCES FOLDER. FOR TASK 14* YOU MUST CREATE THE PROCEDURE FIRST!!!
            //2.FOR TASKS WHICH MODIFY THE DATABASE(SUCH AS 12*, 13*) YOU MIGHT WANT TO DROP THE DATABASE
            // AND SEED IT AGAIN. YOU CAN USE THE SEED METHODS ABOVE(OR BELOW, THEY ARE THE SAME)!
            //3. ALSO CHANGE THE PROPERTIES IN THE APPLICATION.PROPERTIES FILE(user, password, etc)
        //--------------------------------!HOMEWORK STARTS HERE!----------------------------------------
        // SOME TASKS MIGHT TAKE A WHILE TO EXECUTE!
        //BE PATIENT!!!

        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println(Messages.showIntroMessage());

            System.out.println("Enter task number(1-14):");
            System.out.println("!!Enter End if you want to exit the program!!");
            String inputTaskNumber = "";
            while (!"END".equals(inputTaskNumber = bufferedReader.readLine().toUpperCase())) {

                System.out.println("------START OF TASK-------");
                switch (inputTaskNumber) {

                    case "1":
                        //Exe 1
                        printBooks(this.bookService.getAllBooksTitlesByAgeRestriction());
                        break;

                    case "2":
                        //Exe 2
                        printBooks(this.bookService.getAllBooksTitlesByEditionTypeCopiesLessThan());
                        break;

                    case "3":
                        //Exe 3
                        printBooks(this.bookService.getAllBooksByPriceLessThanAndGreaterThan());
                        break;

                    case "4":
                        //Exe 4
                        printBooks(this.bookService.getAllBooksNotReleasedInYear());
                        break;

                    case "5":
                        //Exe 5
                        printBooks(this.bookService.getAllBooksBeforeGivenReleaseDate());
                        break;

                    case "6":
                        //Exe 6
                        printAllAuthors(this.authorService.getAllAuthorFNameEndsWith());
                        break;

                    case "7":
                        //Exe 7
                        printBooks(this.bookService.getAllBooksTitleContains());
                        break;

                    case "8":
                        //Exe 8
                        printBooks(this.bookService.getAllBooksByAuthorLastNamePattern());
                        break;

                    case "9":
                        //Exe 9
                        System.out.println("Count of books with title longer than the entered number: "
                                + this.bookService.getAllBooksCountByTitleLength());
                        break;

                    case "10":
                        //Exe 10
                        printAllAuthors(this.authorService.getAllAuthorsByTotalBooksCopies());
                        break;

                    case "11":
                        //Exe 11 ??? not sure about that
                        printBooks(this.bookService.getAllBooksByTitle());
                        break;

                    case "12":
                        //Exe 12
                        System.out.println(this.bookService.increaseBookCopiesCount());
                        break;

                    case "13":
                        Integer deletedBooks = this.bookService.getDeletedBooks();
                        String output = "";
                        if (deletedBooks == 0){
                            output = String.format("No books were deleted!");
                        }else if (deletedBooks == 1){
                            output = String.format("%d book was deleted!",deletedBooks);
                        }else {
                            output = String.format("%d books were deleted!",deletedBooks);
                        }
                        System.out.println(output);

                        break;

                    case "14":
                        //Exe 14 FOR TASK 14* YOU MUST CREATE THE PROCEDURE FIRST!!! --> RESOURCE FOLDER --> .sql FILE
                        System.out.println("Enter author first and last names: ");
                        String[] names = bufferedReader.readLine().split("\\s+");
                        String fName = names[0];
                        String LName = names[1];
                        Integer authorBooksCount = this.authorService.getAuthorBooksCount(fName, LName);

                        if (authorBooksCount == null) {
                            System.out.printf("%s %s has not written any books!%n", fName, LName);
                        } else if (authorBooksCount == 1) {
                            System.out.printf("%s %s has written %d book.%n", fName, LName, authorBooksCount);
                        } else {
                            System.out.printf("%s %s has written %d books.%n", fName, LName, authorBooksCount);
                        }
                        break;

                    default:
                        System.out.println("No such task number!");
                        break;
                }

                //        this.categoryService.seedCategories();
//        this.authorService.seedAuthorsData();
//         this.bookService.seedBooksData();

                System.out.println("-------END OF TASK-------");
                System.out.println();
                System.out.println(Messages.showIntroMessage());
                System.out.println("Enter task number(1-14): ");
                System.out.println("!!Enter  end   if you want to exit the program!!");
            }
            System.out.println("--------END OF PROGRAM--------");
            System.out.printf("GOODBYE!%nHAVE A NICE DAY!%n:)%n");
        } catch (IOException | NullPointerException e) {
            e.getMessage();
        }

    }

//    private void printAuthor(Integer authorBooksCount) {
//        StringBuilder stringBuilder = new StringBuilder();
//        if (authorBooksCount == 0){
//            stringBuilder.append()
//        }else if (authorBooksCount == 1){
//
//        }else if (authorBooksCount > 1){
//
//        }
//    }

    private void printBooks(List<String> allBooksTitlesByAgeRestriction) {
        allBooksTitlesByAgeRestriction.forEach(System.out::print);
    }

    //    private void printBooksByAuthor() {
//        this.bookService.getAllBooksByAuthor().forEach(book -> {
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
//            String formattedDate = book.getReleaseDate().format(dateTimeFormatter);
//            System.out.printf("Title: %s Release Date: %s Number of copies: %d%n", book.getTitle(),formattedDate,book.getCopies());
//        });
//    }

    private void printAllAuthors(List<String> authors) {
        authors.forEach(System.out::print);
    }

//    private void printAllBooks(List<Book> allBooksAfterCertainYear) {
//        allBooksAfterCertainYear.forEach(book -> System.out.printf("%s%n", book.getTitle()));
//    }

}
