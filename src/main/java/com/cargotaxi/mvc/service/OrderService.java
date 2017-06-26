package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.dao.OrderRepository;
import com.cargotaxi.mvc.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface OrderService  extends AbstractService<Order>{

}
