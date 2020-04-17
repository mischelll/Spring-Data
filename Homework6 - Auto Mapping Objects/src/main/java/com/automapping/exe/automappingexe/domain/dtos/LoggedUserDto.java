package com.automapping.exe.automappingexe.domain.dtos;

import com.automapping.exe.automappingexe.domain.entities.Game;
import com.automapping.exe.automappingexe.domain.entities.Role;

import java.util.List;

public class LoggedUserDto {
    private String email;
    private String password;
    private String fullName;
    private Role role;

    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public LoggedUserDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
