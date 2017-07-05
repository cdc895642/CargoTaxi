package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.dao.CarTypeRepository;
import com.cargotaxi.mvc.model.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CarTypeServiceImpl   extends AbstractServiceImpl<CarType> implements
        CarTypeService{
    @Autowired
    CarTypeRepository carTypeRepository;

    @PostConstruct
    public void init() {
        setRepository(carTypeRepository);
    }

    public CarType findByType(String type){
        return carTypeRepository.findByType(type);
    }

}
