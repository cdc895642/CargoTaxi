package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.CarType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarTypeRepository  extends JpaRepository<CarType, Integer> {
    public CarType findByType(String type);
}
