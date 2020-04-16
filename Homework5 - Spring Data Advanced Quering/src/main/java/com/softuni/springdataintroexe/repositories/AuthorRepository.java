package com.softuni.springdataintroexe.repositories;

import com.softuni.springdataintroexe.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("select  a from Author as a order by size(a.books) desc")
    List<Author> findAuthorByCountOfBooks();

    List<Author> findAllByFirstNameEndsWith(String pattern);

    @Transactional
    @Procedure(name = "authorBooksCount")
    Integer getAuthorBooksCount(@Param("first_name") String first_name, @Param("last_name") String last_name);
}
