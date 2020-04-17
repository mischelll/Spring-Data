package com.automapping.exe.automappingexe.domain.dtos;

public class GameDeleteDto {
    private Long id;

    public GameDeleteDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
