package com.softuni.springdataintroexe.repositories;

import com.softuni.springdataintroexe.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate localDate);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleDesc(String authorFirstName, String authorLastName);

}
