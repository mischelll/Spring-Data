package com.automapping.exe.automappingexe.services.interfaces;

import com.automapping.exe.automappingexe.domain.dtos.GameBuyDto;
import com.automapping.exe.automappingexe.domain.dtos.GameCreateDto;
import com.automapping.exe.automappingexe.domain.dtos.GameDeleteDto;
import com.automapping.exe.automappingexe.domain.dtos.GameEditDto;
import com.automapping.exe.automappingexe.domain.entities.Game;
import com.automapping.exe.automappingexe.domain.views.AllGamesView;
import com.automapping.exe.automappingexe.domain.views.DetailGameView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {
    void addGame(GameCreateDto gameCreateDto);

    void deleteGame(GameDeleteDto gameDeleteDto);

    void editGame(GameEditDto gameEditDto);

    List<AllGamesView> getAllGames();

    DetailGameView getDetailsForGame(String title);

    Game getGameById(Long id);
}
