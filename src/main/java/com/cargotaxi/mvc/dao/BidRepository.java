package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository  extends JpaRepository<Bid, Integer> {
}
