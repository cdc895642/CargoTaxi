package com.cargotaxi.mvc.controller.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NewCarDTO {
    private int userId;
    private int carTypeId;
    private String description;
    private String location;
    private BigDecimal capacity;
    private int load;
    private List<OfferDTO> offers=new ArrayList<>();

    public NewCarDTO(){}

    public List<OfferDTO> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferDTO> offers) {
        this.offers = offers;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(int carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }
}
