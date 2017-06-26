package com.cargotaxi.mvc.controller.form;

import java.math.BigDecimal;

/**
 * use for save data about price and description of price when save info
 * about new car in NewCarDTO
 */
public class OfferDTO {
    private BigDecimal price;
    private String description;

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
}
