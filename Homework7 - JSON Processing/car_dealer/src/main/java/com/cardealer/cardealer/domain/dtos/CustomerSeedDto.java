package com.cardealer.cardealer.domain.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.util.Date;

public class CustomerSeedDto {
    @Expose
    private String name;
    @Expose
    private String birthDate;
    @Expose
    private Boolean isYoungDriver;

    public CustomerSeedDto() {
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
