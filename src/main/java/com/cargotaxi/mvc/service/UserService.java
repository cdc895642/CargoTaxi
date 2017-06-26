package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.controller.form.NewUserDTO;
import com.cargotaxi.mvc.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.List;

/**
 * specific methods for operations with users
 */
public interface UserService<E> extends AbstractService<E>{
    public E registerNewUser(NewUserDTO newUserDTO);
    public boolean isLoginExist(String login);
    public List<E> findAll(Specification<User> condition);
    public List<E> findExecutorsAll();
    @Transactional
    public User findByLogin(String login);
}
