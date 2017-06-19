package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {
}
