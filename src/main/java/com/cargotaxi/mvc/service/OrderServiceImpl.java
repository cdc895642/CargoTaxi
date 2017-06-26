package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.dao.OrderRepository;
import com.cargotaxi.mvc.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class OrderServiceImpl extends AbstractServiceImpl<Order> implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @PostConstruct
    public void init() {
        setRepository(orderRepository);
    }
}
