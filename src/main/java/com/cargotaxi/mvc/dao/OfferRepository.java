package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository  extends JpaRepository<Offer, Integer> {
}
