package com.cardealer.cardealer.domain.dtos;

import com.google.gson.annotations.Expose;

public class LocalSupplierDto {
    @Expose
    private Long Id;
    @Expose
    private String Name;
    @Expose
    private int partsCount;

    public LocalSupplierDto() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }
}
