package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.controller.form.NewUserDTO;
import com.cargotaxi.mvc.dao.UserRepository;
import com.cargotaxi.mvc.model.Phone;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.model.UserCar;
import com.cargotaxi.mvc.model.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUser(NewUserDTO newUserDTO) {
        User user = new User();
        user.setLogin(newUserDTO.getLogin());
        user.setEmail(newUserDTO.getEmail());
        Set<Phone> phones;
        if (newUserDTO.getPhones() != null
                || newUserDTO.getPhones().size() > 0) {
            phones = new HashSet<>();
            newUserDTO.getPhones().forEach(p -> phones.add(new Phone(p
                    .getNumber(),user)));
        } else {
            phones = null;
        }
        user.setPhones(phones);
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        return userRepository.save(user);
    }

    public boolean isLoginExist(String login) {
        User user = userRepository.findByLogin(login);
        return user != null;
    }

    public User findById(int id) {
        return userRepository.findOne(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findExecutorsAll() {
        return userRepository.findAll(isExecutor());
        //return userRepository.findExecutorAll();
    }

    private Specification<User> isExecutor() {
        return (root, query, builder) -> {
            return builder.gt(builder.size(root.get("cars")), 0);
        };
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        throw new UnsupportedOperationException("delete not supported");
    }
}
