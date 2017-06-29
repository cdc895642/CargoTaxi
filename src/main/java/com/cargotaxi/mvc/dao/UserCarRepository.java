package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserCarRepository extends JpaRepository<UserCar, Integer>,
        JpaSpecificationExecutor {
}
