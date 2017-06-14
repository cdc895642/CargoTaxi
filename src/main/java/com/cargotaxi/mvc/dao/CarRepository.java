package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository  extends JpaRepository<Car, Integer> {
}
