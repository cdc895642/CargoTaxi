package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
