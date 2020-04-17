package com.automapping.exe.automappingexe.services.impls;

import com.automapping.exe.automappingexe.domain.dtos.GameBuyDto;
import com.automapping.exe.automappingexe.domain.dtos.GameCreateDto;
import com.automapping.exe.automappingexe.domain.dtos.GameDeleteDto;
import com.automapping.exe.automappingexe.domain.dtos.GameEditDto;
import com.automapping.exe.automappingexe.domain.entities.Game;
import com.automapping.exe.automappingexe.domain.views.AllGamesView;
import com.automapping.exe.automappingexe.domain.views.DetailGameView;
import com.automapping.exe.automappingexe.repositories.GameRepository;
import com.automapping.exe.automappingexe.services.interfaces.GameService;
import com.automapping.exe.automappingexe.services.interfaces.UserService;
import com.automapping.exe.automappingexe.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    private final ModelMapper modelMapper;
    private final GameRepository gameRepository;
    private final ValidationUtil validationUtil;
    private final UserService userService;

    public GameServiceImpl(ModelMapper modelMapper, GameRepository gameRepository, ValidationUtil validationUtil, UserService userService) {
        this.modelMapper = modelMapper;
        this.gameRepository = gameRepository;
        this.validationUtil = validationUtil;
        this.userService = userService;
    }


    @Override
    public void addGame(GameCreateDto gameCreateDto) {
        if (!isUserAdmin()) {
            System.out.println("Logged user is not ADMIN!");
            return;
        }

        Game searchByTtitle = this.gameRepository.findByTitle(gameCreateDto.getTitle());
        if (searchByTtitle != null) {
            System.out.println("Game already exists!");
            return;
        }

        boolean valid = this.validationUtil.isValid(gameCreateDto);
        if (!valid) {
            this.validationUtil
                    .getViolations(gameCreateDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        Game game = this.modelMapper.map(gameCreateDto, Game.class);
        this.gameRepository.saveAndFlush(game);

        System.out.printf("Added %s%n", game.getTitle());
    }

    private boolean isUserAdmin() {
        return this.userService.checkForAdmin();
    }

    @Override
    public void deleteGame(GameDeleteDto gameDeleteDto) {
        if (!isUserAdmin()) {
            return;
        }

        Optional<Game> byId = this.gameRepository.findById(gameDeleteDto.getId());
        if (byId.isEmpty()) {
            System.out.println("Invalid id!");
            return;
        }

        this.gameRepository.deleteById(gameDeleteDto.getId());
        System.out.printf("Deleted %s%n", byId.get().getTitle());
    }

    @Override
    public void editGame(GameEditDto gameEditDto) {
        if (!isUserAdmin()) {
            return;
        }

        Optional<Game> byId = this.gameRepository.findById(gameEditDto.getId());
        if (byId.isEmpty()) {
            System.out.println("Invalid id!");
            return;
        }

        updateGame(byId, gameEditDto);
        this.gameRepository.saveAndFlush(byId.get());

        System.out.printf("Edited %s.%n", byId.get().getTitle());
    }

    @Override
    public List<AllGamesView> getAllGames() {
        List<Game> all = this.gameRepository.findAll();
        List<AllGamesView> allGamesViews = new LinkedList<AllGamesView>();

        for (Game game : all) {
            AllGamesView map = this.modelMapper.map(game, AllGamesView.class);
            allGamesViews.add(map);
        }

        return allGamesViews;
    }

    @Override
    public DetailGameView getDetailsForGame(String title) {
        Game byTitle = this.gameRepository.findByTitle(title);

        return this.modelMapper.map(byTitle, DetailGameView.class);
    }

    @Override
    public Game getGameById(Long id) {

        return this.gameRepository.findById(id).get();
    }


    private void updateGame(Optional<Game> byId, GameEditDto gameEditDto) {
        if (gameEditDto.getDescription() != null) {
            byId.get().setDescription(gameEditDto.getDescription());
        }
        if (gameEditDto.getPrice() != null) {
            byId.get().setPrice(gameEditDto.getPrice());
        }
        if (gameEditDto.getReleaseDate() != null) {
            byId.get().setReleaseDate(gameEditDto.getReleaseDate());
        }
        if (gameEditDto.getSize() != null) {
            byId.get().setSize(gameEditDto.getSize());
        }
        if (gameEditDto.getThumbnail() != null) {
            byId.get().setThumbnail(gameEditDto.getThumbnail());
        }
        if (gameEditDto.getTitle() != null) {
            byId.get().setTitle(gameEditDto.getTitle());
        }
        if (gameEditDto.getTrailer() != null) {
            byId.get().setTrailer(gameEditDto.getTrailer());
        }

    }


}



