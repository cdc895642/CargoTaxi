package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.dao.CarRepository;
import com.cargotaxi.mvc.model.Car;
import com.cargotaxi.mvc.model.UserCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CarServiceImpl extends AbstractServiceImpl<Car> implements
        CarService  {
    @Autowired
    CarRepository carRepository;

    @PostConstruct
    public void init() {
        setRepository(carRepository);
    }
}
