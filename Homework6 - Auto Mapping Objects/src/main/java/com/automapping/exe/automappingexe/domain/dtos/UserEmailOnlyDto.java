package com.automapping.exe.automappingexe.domain.dtos;

public class UserEmailOnlyDto {
    private String email;

    public UserEmailOnlyDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
