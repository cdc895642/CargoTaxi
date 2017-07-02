package com.cargotaxi.mvc.controller.form;

import com.cargotaxi.mvc.model.UserCar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NewCarDTO {

    private int userCarId=-1;
    private int userId=-1;
    private int carTypeId;
    private String description;
    private String location;
    private BigDecimal capacity;
    private int load;
    private List<OfferDTO> offers=new ArrayList<>();

    public NewCarDTO(){}

    public NewCarDTO(UserCar car){
        userCarId=car.getId();
        userId=car.getUser().getId();
        carTypeId=car.getCar().getCarType().getId();
        description=car.getDescription();
        location=car.getLocation();
        capacity=car.getCar().getCapacity();
        load=car.getCar().getLoad();
        offers=new ArrayList<>();
        car.getOffers().forEach(o->offers.add(
                new OfferDTO(){
                    {
                        setPrice(o.getPrice());
                        setDescription(o.getDescription());
                    }}));
    }

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

    public int getUserCarId() {
        return userCarId;
    }

    public void setUserCarId(int userCarId) {
        this.userCarId = userCarId;
    }
}
