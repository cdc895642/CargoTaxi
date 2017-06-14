package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.dao.CarTypeRepository;
import com.cargotaxi.mvc.model.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarTypeService {
    @Autowired
    CarTypeRepository carTypeRepository;

    public CarType findById(int id){
        return carTypeRepository.findOne(id);
    }

    public List<CarType> findAll(){
        return carTypeRepository.findAll();
    }

    public CarType create(CarType carType){
        return carTypeRepository.save(carType);
    }

    public CarType update(CarType carType){
        return carTypeRepository.save(carType);
    }

    public void delete (CarType carType){
        throw new UnsupportedOperationException("delete not supported");
    }
}
