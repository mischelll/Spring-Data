package com.softuni.springdataintroexe.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "authors")
@NamedStoredProcedureQuery(
        name = "authorBooksCount",
        procedureName = "udp_author_total_books_written",

        parameters = {
                @StoredProcedureParameter(
                        name = "first_name",
                        type = String.class,
                        mode = ParameterMode.IN),
                @StoredProcedureParameter(
                        name = "last_name",
                        type = String.class,
                        mode = ParameterMode.IN),
                @StoredProcedureParameter(
                        mode = ParameterMode.OUT,
                        name = "b_count",
                        type = Integer.class
                        )})

public class Author extends BaseEntity {
    private String firstName;
    private String lastName;

    private Set<Book> books;


    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @OneToMany(mappedBy = "author", targetEntity = Book.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
