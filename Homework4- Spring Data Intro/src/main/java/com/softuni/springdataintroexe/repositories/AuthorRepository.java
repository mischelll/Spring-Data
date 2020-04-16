package com.softuni.springdataintroexe.repositories;

import com.softuni.springdataintroexe.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("select  a from Author as a order by a.books.size desc")
    List<Author> findAuthorByCountOfBooks();
}
