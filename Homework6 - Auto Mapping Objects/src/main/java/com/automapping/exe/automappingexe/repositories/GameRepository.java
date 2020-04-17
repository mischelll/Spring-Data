package com.automapping.exe.automappingexe.repositories;

import com.automapping.exe.automappingexe.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByTitle(String title);

    Optional<Game> findById(Long id);

    @Modifying
    @Transactional
    void deleteById(Long id);

    List<Game> findAll();


}
