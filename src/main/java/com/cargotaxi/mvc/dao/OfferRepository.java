package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.Offer;
import com.cargotaxi.mvc.model.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface OfferRepository  extends JpaRepository<Offer, Integer> {
    public Set<Offer> findByUserCar(UserCar userCar);
}
