package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.controller.form.NewCarDTO;
import com.cargotaxi.mvc.model.UserCar;

import java.security.Principal;

public interface UserCarService<T>   extends AbstractService<T>{
    public UserCar createNewCar(NewCarDTO newCarDTO, Principal user);
}
