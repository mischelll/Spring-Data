package com.automapping.exe.automappingexe.domain.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.lang.annotation.Retention;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GameCreateDto {
    private String title;
    private String trailer;
    private String thumbnail;
    private Double size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public GameCreateDto(String title, String trailer, String thumbnail, Double size, BigDecimal price, String description, LocalDate releaseDate) {
        this.title = title;
        this.trailer = trailer;
        this.thumbnail = thumbnail;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public GameCreateDto() {
    }

    @Pattern(regexp = "^[A-Z]{1}[A-Za-z 0-9]{2,100}$", message = "Invalid title!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Pattern(regexp = "^[A-Za-z0-9]{11}$", message = "Invalid trailer!")
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Pattern(regexp = "^(http|https):\\/\\/[A-Za-z0-9\\-\\/#$%&*@!)(-\\.]+$", message = "Invalid thumbnailURL!")
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Positive(message = "Positive size required!")
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Positive(message = "Price must be a positive number!")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Length(min = 20)
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
