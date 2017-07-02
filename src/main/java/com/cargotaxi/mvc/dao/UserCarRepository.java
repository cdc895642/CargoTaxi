package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCarRepository extends JpaRepository<UserCar, Integer>,
        JpaSpecificationExecutor {

    @Query("Select car from UserCar car where car.user.login = :login ")
    public List<UserCar> findByUserLogin(@Param("login") String login);
}
