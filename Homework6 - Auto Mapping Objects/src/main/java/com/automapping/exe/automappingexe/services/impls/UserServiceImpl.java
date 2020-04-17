package com.automapping.exe.automappingexe.services.impls;

import com.automapping.exe.automappingexe.domain.dtos.LoggedUserDto;
import com.automapping.exe.automappingexe.domain.dtos.UserLogInDto;
import com.automapping.exe.automappingexe.domain.dtos.UserRegisterDto;
import com.automapping.exe.automappingexe.domain.entities.Game;
import com.automapping.exe.automappingexe.domain.entities.Role;
import com.automapping.exe.automappingexe.domain.entities.User;
import com.automapping.exe.automappingexe.repositories.UserRepository;
import com.automapping.exe.automappingexe.services.interfaces.GameService;
import com.automapping.exe.automappingexe.services.interfaces.OrderService;
import com.automapping.exe.automappingexe.services.interfaces.UserService;
import com.automapping.exe.automappingexe.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private LoggedUserDto loggedUserDto;
    private final GameService gameService;
    private final OrderService orderService;

    private boolean createOrder;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, ValidationUtil validationUtil, @Lazy GameService gameService, @Lazy OrderService orderService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
        this.gameService = gameService;
        this.orderService = orderService;
        createOrder = false;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {

        User searchByEmail = this.userRepository.findByEmail(userRegisterDto.getEmail());
        if (searchByEmail != null) {
            System.out.println("User already exists!");
            return;
        }

        boolean valid = this.validationUtil.isValid(userRegisterDto);
        if (!valid) {
            this.validationUtil
                    .getViolations(userRegisterDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user = this.modelMapper.map(userRegisterDto, User.class);
        user.setRole(this.userRepository.count() == 0 ? Role.ADMIN : Role.USER);

        this.userRepository.saveAndFlush(user);

    }

    @Override
    public void logInUser(UserLogInDto userLogInDto) {
        User user = this.userRepository
                .findByEmail(userLogInDto.getEmail());
        String result = "";

        if (user == null || (!user.getPassword().equals(userLogInDto.getPassword()))) {
            result = "Incorrect username / password";
        } else {

            if (this.loggedUserDto != null) {
                result = "An user is already logged in! Cannot log in another user!";
            } else {
                this.loggedUserDto = this.modelMapper.map(user, LoggedUserDto.class);
                result = String.format("Successfully logged in %s", loggedUserDto.getFullName());
            }

        }

        System.out.println(result);
    }

    @Override
    public void logOutUser() {
        String result = "";
        if (this.loggedUserDto == null) {
            result = "Cannot log out. No user was logged in.";
        } else {
            String loggedUserName = this.loggedUserDto.getFullName();
            this.loggedUserDto = null;
            result = String.format("User %s successfully logged out.", loggedUserName);
        }

        System.out.println(result);
    }

    @Override
    public boolean checkForAdmin() {
        if (this.loggedUserDto == null) {
            return false;
        }
        return this.loggedUserDto.getRole().equals(Role.ADMIN);
    }

    @Override
    public void buyGame(Long userId, Long gameToBuyId) {
        Optional<User> byId = this.userRepository.findById(userId);
        if (byId.isEmpty()) {
            this.createOrder = false;

            System.out.println("No user with given ID!");
            return;
        }

        if (loggedUserDto == null){
            this.createOrder = false;


            System.out.println("No logged user!");
            return;
        }

        boolean equals = this.loggedUserDto.getEmail().equals(byId.get().getEmail());
        if (!equals) {
            this.createOrder = false;
            System.out.println("User must be logged in to buy/order a game!");
            return;
        }

        Game gameById = this.gameService.getGameById(gameToBuyId);
        boolean contains = byId.get().getGames().contains(gameById);
        if (contains) {
            this.createOrder = false;
            System.out.println("User already bought this game!");
            return;
        }

        this.createOrder = true;
        //    byId.get().getGames().add(gameById);
//        this.userRepository.saveAndFlush(byId.get());


        System.out.printf("User %s bought game %s%n", byId.get().getFullName(), gameById.getTitle());
    }

    @Override
    public User getUserById(Long userId) {
        return this.userRepository.findById(userId).get();
    }

    @Override
    public boolean toCreateOrder() {
        return createOrder;
    }

    @Override
    public String showOwnedGames() {
        if (loggedUserDto == null){
            return "No logged user!";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Games the user owns:").append(System.lineSeparator());
        for (Game game : this.loggedUserDto.getGames()) {
            stringBuilder.append(game.getTitle()).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }


}
