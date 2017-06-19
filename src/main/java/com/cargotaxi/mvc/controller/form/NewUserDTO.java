package com.cargotaxi.mvc.controller.form;

import com.cargotaxi.mvc.validation.PasswordMatches;
import org.hibernate.validator.constraints.Email;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@PasswordMatches
public class NewUserDTO {
    @Size(min = 5, max = 15, message = "{valid.error.register.login.size}")
    private String login;

    @Size(min = 5, max = 30)
    private String password;

    @Size(min = 5, max = 30)
    private String matchingPassword;

    @Email
    private String email;

    @Valid
    private List<PhoneDTO> phones=new ArrayList<>();

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
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

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }
}
