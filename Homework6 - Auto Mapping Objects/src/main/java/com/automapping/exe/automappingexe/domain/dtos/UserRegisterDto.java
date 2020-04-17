package com.automapping.exe.automappingexe.domain.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterDto {
    private String email;
    private String password;
    private String fullName;

    public UserRegisterDto(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public UserRegisterDto() {
    }

    @Pattern(regexp = "^[a-z0-9\\_\\-]+\\.*[a-z0-9]+@{1}[a-z0-9]+\\.+[a-z0-9]+$", message = "Not a valid email!")
    @Size(min = 6, max = 16, message = "Length of password not valid!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Pattern(regexp = "[A-Z]+[a-z]+[0-9]+", message = "Invalid password!")
    @Size(min = 6, message = "Pass length not valid!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "Name must not be null!")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
