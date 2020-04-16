package com.softuni.springdataintroexe.repositories;

import com.softuni.springdataintroexe.domain.entities.AgeRestriction;
import com.softuni.springdataintroexe.domain.entities.Book;
import com.softuni.springdataintroexe.domain.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate localDate);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleDesc(String authorFirstName, String authorLastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesIsLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceIsLessThanOrPriceGreaterThan(BigDecimal priceLess, BigDecimal priceGreater);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate dateBefore, LocalDate dateAfter);

    List<Book> findAllByTitleContains(String pattern);

    @Query(value = "select b from Book as b join b.author a where a.lastName like concat(:pattern, '%') ")
    List<Book> findAllByAuthorLastNameStartsWith(@Param("pattern") String pattern);

    @Query(value = "select b from Book as b where length(b.title) > :number")
    List<Book> findAllByTitleLength(@Param("number") Integer number);

    List<Book> findAllByTitle(String title);

    @Modifying
    @Query(value = "UPDATE Book as b set b.copies = b.copies + :number " +
            "where b.releaseDate > :date")
   void increaseBookCopiesWithReleaseDateAfter(@Param("number") Integer number,
                                                      @Param("date") LocalDate date);


    @Modifying
    @Query
    Integer deleteBookByCopiesIsLessThan(Integer number);



}
