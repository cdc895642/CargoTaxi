package com.cargotaxi.mvc.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;


/**
 * Created by cdc89 on 12.06.2017.
 */
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
