package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.controller.form.NewCarDTO;
import com.cargotaxi.mvc.model.UserCar;

import java.security.Principal;
import java.util.List;

public interface UserCarService extends AbstractService<UserCar>{
    public UserCar createNewCar(NewCarDTO newCarDTO, Principal user);
    public List<UserCar> findUserCarsOfPrincipal(Principal user);
//    public void deleteById(int id);
}
