package com.cargotaxi.mvc.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "user_cars")
public class UserCar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "description")
    @Size(max = 2000)
    private String description;

    @Column(name = "location")
    @Size(max = 200)
    private String location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="userCar")
    private Set<Offer> offers;

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
