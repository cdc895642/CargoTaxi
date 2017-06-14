package com.cargotaxi.mvc.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @NotEmpty
    @Size(min = 5, max = 15)
    @Column(name = "login", unique = true)
    private String login;

    @NotEmpty
    @Size(min = 5, max = 30)
    @Column(name = "password")
    private String password;

    @Email
    @Size(min = 5, max = 30)
    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            mappedBy="executor")
    private Set<Bid> bids;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            mappedBy="user")
    private Set<Order> orders;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            mappedBy="user")
    private Set<UserCar> cars;

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<UserCar> getCars() {
        return cars;
    }

    public void setCars(Set<UserCar> cars) {
        this.cars = cars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
