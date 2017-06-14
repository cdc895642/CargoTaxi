package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
