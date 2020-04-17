package com.automapping.exe.automappingexe.domain.views;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DetailGameView {
    private String title;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public DetailGameView() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
