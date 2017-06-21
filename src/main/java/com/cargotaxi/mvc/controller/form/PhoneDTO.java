package com.cargotaxi.mvc.controller.form;

import javax.validation.constraints.Size;

/**
 * use for save and validate user input data about phones
 */
public class PhoneDTO {
    @Size(min = 13, max = 13, message = "{valid.phone.size}")
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
