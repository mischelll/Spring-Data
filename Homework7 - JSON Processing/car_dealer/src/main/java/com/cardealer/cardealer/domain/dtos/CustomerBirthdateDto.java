package com.cardealer.cardealer.domain.dtos;

import com.cardealer.cardealer.domain.entities.Sale;
import com.google.gson.annotations.Expose;

import java.time.LocalDate;
import java.util.Set;

public class CustomerBirthdateDto {
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private String birthDate;
    @Expose
    private Boolean isYoungDriver;
    @Expose
    private Set<SaleViewDto> purchases;

    public CustomerBirthdateDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public Set<SaleViewDto> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<SaleViewDto> purchases) {
        this.purchases = purchases;
    }
}
