package com.automapping.exe.automappingexe.runner;

import com.automapping.exe.automappingexe.domain.dtos.*;
import com.automapping.exe.automappingexe.domain.views.AllGamesView;
import com.automapping.exe.automappingexe.domain.views.DetailGameView;
import com.automapping.exe.automappingexe.services.interfaces.GameService;
import com.automapping.exe.automappingexe.services.interfaces.OrderService;
import com.automapping.exe.automappingexe.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class AppRunner implements CommandLineRunner {

    private final UserService userService;
    private final BufferedReader bufferedReader;
    private final ModelMapper modelMapper;
    private final GameService gameService;
    private final OrderService orderService;

    @Autowired
    public AppRunner(UserService userService, BufferedReader bufferedReader, ModelMapper modelMapper, GameService gameService, OrderService orderService) {
        this.userService = userService;
        this.bufferedReader = bufferedReader;
        this.modelMapper = modelMapper;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            try {
                System.out.println("Enter command(Enter  ->End<-  if you want to exit the program):");
                String[] input = this.bufferedReader.readLine().split("\\|");


                switch (input[0]) {
                    case "RegisterUser":
                        if (!input[2].equals(input[3])) {
                            System.out.println("Passwords don't match!");
                            break;
                        }
                        UserRegisterDto userRegisterDto = new UserRegisterDto();
                        userRegisterDto.setEmail(input[1]);
                        userRegisterDto.setPassword(input[2]);
                        userRegisterDto.setFullName(input[4]);

                        this.userService.registerUser(userRegisterDto);

                        break;

                    case "LoginUser":
                        UserLogInDto userLogInDto = new UserLogInDto(input[1], input[2]);
                        this.userService.logInUser(userLogInDto);
                        break;

                    case "Logout":
                        this.userService.logOutUser();
                        break;

                    case "AddGame":
                        GameCreateDto gameCreateDto = new GameCreateDto();
                        gameCreateDto.setTitle(input[1]);
                        gameCreateDto.setPrice(BigDecimal.valueOf(Double.parseDouble(input[2])));
                        gameCreateDto.setSize(Double.parseDouble(input[3]));
                        gameCreateDto.setTrailer(input[4]);
                        gameCreateDto.setThumbnail(input[5]);
                        gameCreateDto.setDescription(input[6]);
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        gameCreateDto.setReleaseDate(LocalDate.parse(input[7], dateTimeFormatter));

                        this.gameService.addGame(gameCreateDto);
                        break;

                    case "DeleteGame":
                        GameDeleteDto gameDeleteDto = new GameDeleteDto(Long.parseLong(input[1]));

                        this.gameService.deleteGame(gameDeleteDto);
                        break;

                    case "EditGame":
                        GameEditDto gameEditDto = setDto(input);
                        gameEditDto.setId(Long.parseLong(input[1]));

                        this.gameService.editGame(gameEditDto);
                        break;

                    case "AllGames":
                        List<AllGamesView> allGames = this.gameService.getAllGames();
                        allGames.forEach(allGamesView -> System.out.printf("%s %.2f%n",
                                allGamesView.getTitle(),
                                allGamesView.getPrice()));
                        break;
                    case "DetailGame":
                        DetailGameView detailsForGame = this.gameService.getDetailsForGame(input[1]);
                        System.out.printf("Title: %s%nPrice: %.2f%nDescription: %s%nRelease date: %s%n",
                                detailsForGame.getTitle(),
                                detailsForGame.getPrice(),
                                detailsForGame.getDescription(),
                                detailsForGame.getReleaseDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        break;

                    case "BuyGame":
                        Long userId = Long.parseLong(input[1]);
                        Long gameToBuyId = Long.parseLong(input[2]);

                        this.userService.buyGame(userId, gameToBuyId);
                        this.orderService.createOrder(userId, gameToBuyId);
                        break;
                    case "OwnedGames":
                        System.out.println(this.userService.showOwnedGames());
                        break;
                    case "End":
                        return;


                    default:
                        System.out.println("Invalid command!");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private GameEditDto setDto(String[] input) {
        GameEditDto gameEditDto = new GameEditDto();
        for (int i = 2; i < input.length; i++) {
            String[] values = input[i].split("=");
            String check = values[0];
            switch (check) {
                case "price":
                    gameEditDto.setPrice(BigDecimal.valueOf(Double.parseDouble(values[1])));
                    break;
                case "size":
                    gameEditDto.setSize(Double.parseDouble(values[1]));
                    break;
                case "title":
                    gameEditDto.setTitle(values[1]);
                    break;
                case "trailer":
                    gameEditDto.setTrailer(values[1]);
                    break;
                case "thumbnail":
                    gameEditDto.setThumbnail(values[1]);
                    break;
                case "description":
                    gameEditDto.setDescription(values[1]);
                    break;
            }
        }

        return gameEditDto;
    }
}
