package com.cardealer.cardealer.domain.dtos;

import com.cardealer.cardealer.domain.entities.Part;
import com.google.gson.annotations.Expose;

import java.util.Set;

public class CarPartsDto {
    @Expose
    private CarViewDto car;
    @Expose
    private Set<PartViewDto> partSet;

    public CarPartsDto() {
    }

    public CarViewDto getCar() {
        return car;
    }

    public void setCar(CarViewDto car) {
        this.car = car;
    }

    public Set<PartViewDto> getPartSet() {
        return partSet;
    }

    public void setPartSet(Set<PartViewDto> partSet) {
        this.partSet = partSet;
    }
}
