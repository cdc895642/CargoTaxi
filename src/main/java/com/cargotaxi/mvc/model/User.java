package com.cargotaxi.mvc.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable{

    private int id;
    private String login;
    private String password;
    private String email;
    private Set<Bid> bids;
    private Set<Order> orders;
    private Set<UserCar> cars;
    private Set<Role> roles=new HashSet<>();
    private Set<Phone> phones;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            mappedBy = "user")
    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles",
            //foreign key
            joinColumns = {@JoinColumn(name = "user_id")},
            //foreign key for other side
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            mappedBy = "executor")
    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            mappedBy = "user")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @OneToMany(fetch = FetchType.EAGER, //cascade = CascadeType.ALL,
            mappedBy = "user")
    public Set<UserCar> getCars() {
        return cars;
    }

    public void setCars(Set<UserCar> cars) {
        this.cars = cars;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotEmpty
    @Size(min = 5, max = 15)
    @Column(name = "login", unique = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @NotEmpty
    @Size(min = 5, max = 200)
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Email
    @Size(min = 5, max = 30)
    @Column(name = "email", unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
