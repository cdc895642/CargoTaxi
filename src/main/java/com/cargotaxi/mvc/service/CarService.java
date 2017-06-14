package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.dao.CarRepository;
import com.cargotaxi.mvc.model.Car;
import com.cargotaxi.mvc.model.UserCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    public Car findById(int id){
        return carRepository.findOne(id);
    }

    public List<Car> findAll(){
        return carRepository.findAll();
    }

    public Car create(Car userCar){
        return carRepository.save(userCar);
    }

    public Car update(Car userCar){
        return carRepository.save(userCar);
    }

    public void delete (Car userCar){
        throw new UnsupportedOperationException("delete not supported");
    }
}
