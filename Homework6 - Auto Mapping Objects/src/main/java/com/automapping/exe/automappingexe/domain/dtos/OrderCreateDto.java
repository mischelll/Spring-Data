package com.automapping.exe.automappingexe.domain.dtos;

import com.automapping.exe.automappingexe.domain.entities.Game;
import com.automapping.exe.automappingexe.domain.entities.User;

import java.util.Set;

public class OrderCreateDto {
    private User user;
    private Set<Game> games;

    public OrderCreateDto() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
