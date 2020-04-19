package com.cardealer.cardealer.domain.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    private String make;
    private String model;
    private Long travelledDistance;

    private Set<Part> parts;


    public Car() {

    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "parts_cars",
            joinColumns = {@JoinColumn(name = "car_id")},
            inverseJoinColumns = {@JoinColumn(name = "part_id")})
    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    @Column
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "travelled_distance")
    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}
