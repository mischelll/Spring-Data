package com.automapping.exe.automappingexe.domain.views;

import java.math.BigDecimal;

public class AllGamesView {
    private String title;
    private BigDecimal price;

    public AllGamesView(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public AllGamesView() {
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
}
