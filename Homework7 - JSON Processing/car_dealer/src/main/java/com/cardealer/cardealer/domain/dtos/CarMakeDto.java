package com.cardealer.cardealer.domain.dtos;

import com.google.gson.annotations.Expose;

public class CarMakeDto {
    @Expose
    private Long Id;
    @Expose
    private String Make;
    @Expose
    private String Model;
    @Expose
    private Long TravelledDistance;

    public CarMakeDto() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public Long getTravelledDistance() {
        return TravelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        TravelledDistance = travelledDistance;
    }
}
