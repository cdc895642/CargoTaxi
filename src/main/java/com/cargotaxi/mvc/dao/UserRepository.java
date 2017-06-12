package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by cdc89 on 12.06.2017.
 */
//@Transactional
//@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
