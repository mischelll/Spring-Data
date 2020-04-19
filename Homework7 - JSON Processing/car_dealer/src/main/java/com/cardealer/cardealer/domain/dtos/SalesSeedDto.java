package com.cardealer.cardealer.domain.dtos;

import com.cardealer.cardealer.domain.entities.Car;
import com.cardealer.cardealer.domain.entities.Customer;
import com.google.gson.annotations.Expose;

public class SalesSeedDto {
    @Expose
    private Customer customer;
    @Expose
    private Car car;
    @Expose
    private Double discountPercentage;

    public SalesSeedDto() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
