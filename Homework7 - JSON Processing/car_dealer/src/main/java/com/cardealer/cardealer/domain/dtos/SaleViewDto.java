package com.cardealer.cardealer.domain.dtos;

import com.cardealer.cardealer.domain.entities.Car;
import com.google.gson.annotations.Expose;
import org.springframework.core.Ordered;

public class SaleViewDto {
    @Expose
    private CarViewDto car;
    @Expose
    private Double discountPercentage;

    public SaleViewDto() {
    }

    public CarViewDto getCar() {
        return car;
    }

    public void setCar(CarViewDto car) {
        this.car = car;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
