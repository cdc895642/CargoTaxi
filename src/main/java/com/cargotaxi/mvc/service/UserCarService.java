package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.dao.UserCarRepository;
import com.cargotaxi.mvc.model.UserCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCarService {
    @Autowired
    UserCarRepository userCarRepository;

    public UserCar findById(int id){
        return userCarRepository.findOne(id);
    }

    public List<UserCar> findAll(){
        return userCarRepository.findAll();
    }

    public UserCar create(UserCar userCar){
        return userCarRepository.save(userCar);
    }

    public UserCar update(UserCar userCar){
        return userCarRepository.save(userCar);
    }

    public void delete (UserCar userCar){
        throw new UnsupportedOperationException("delete not supported");
    }
}
