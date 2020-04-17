package com.automapping.exe.automappingexe.services.interfaces;

import com.automapping.exe.automappingexe.domain.dtos.GameBuyDto;
import com.automapping.exe.automappingexe.domain.dtos.UserLogInDto;
import com.automapping.exe.automappingexe.domain.dtos.UserRegisterDto;
import com.automapping.exe.automappingexe.domain.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void logInUser(UserLogInDto userLogInDto);

    void logOutUser();

    boolean checkForAdmin();

    void buyGame(Long userId, Long gameToBuyId);

    User getUserById(Long userId);

    boolean toCreateOrder();

    String showOwnedGames();


}
