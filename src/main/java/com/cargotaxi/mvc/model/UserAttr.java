package com.cargotaxi.mvc.model;

/**
 * Created by cdc89 on 14.06.2017.
 */
public class UserAttr{

    public int id;
    public User user;
    public int carType;

    public int getCarType() {
        return carType;
    }

    public void setCarType(int carType) {
        this.carType = carType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //    public UserAttr(){}
//
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
