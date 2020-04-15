package com.softuni.springdataintroexe.services.impl;

import com.softuni.springdataintroexe.domain.entities.Author;
import com.softuni.springdataintroexe.repositories.AuthorRepository;
import com.softuni.springdataintroexe.services.interfaces.AuthorService;
import com.softuni.springdataintroexe.utils.FileReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.softuni.springdataintroexe.constants.GlobalConstants.*;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final FileReader fileReader;

    public AuthorServiceImpl(AuthorRepository authorRepository, FileReader fileReader) {
        this.authorRepository = authorRepository;
        this.fileReader = fileReader;
    }

    @Override
    public void seedAuthorsData() throws IOException {
        String[] fileContent = fileReader.readFileContent(AUTHORS_FILE_PATH);

        if (this.authorRepository.count() != 0) {
            List<Author> all = this.authorRepository.findAll();
            for (String s : fileContent) {
                String[] params = s.split("\\s+");
                boolean check = false;
                for (Author author : all) {
                    if (author.getFirstName().equals(params[0]) && author.getLastName().equals(params[1])) {
                        check = true;
                        break;
                    }
                }

                if (!check) {
                    Author author1 = new Author(params[0], params[1]);
                    this.authorRepository.saveAndFlush(author1);
                }
            }
        } else {
            Arrays.stream(fileContent).forEach(a -> {
                String[] parameters = a.split("\\s+");
                Author author = new Author(parameters[0], parameters[1]);
                this.authorRepository.saveAndFlush(author);
            });
        }

    }

    @Override
    public int getAuthorsCount() {
        return (int) this.authorRepository.count();
    }

    @Override
    public Author findAuthorById(long id) {
        return this.authorRepository.getOne(id);
    }

    @Override
    public List<String> authorsByCountOfTheirBooks() {
        List<Author> authors = this.authorRepository.findAuthorByCountOfBooks();

        return authors
                .stream()
                .map(author -> String.format("%s %s %d%n"
                        , author.getFirstName()
                        , author.getFirstName()
                        , author.getBooks().size())).collect(Collectors.toList());
    }


}
