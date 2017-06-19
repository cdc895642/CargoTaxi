package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>,
        JpaSpecificationExecutor {
    @Query("Select u from User u where u.cars IS NOT EMPTY")
    public List<User> findExecutorAll();

    public User findByLogin(String login);
}
