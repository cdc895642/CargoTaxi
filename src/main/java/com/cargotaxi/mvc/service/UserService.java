package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.dao.UserRepository;
import com.cargotaxi.mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User findById(int id){
        return userRepository.findOne(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User create(User user){
        return userRepository.save(user);
    }

    public User update(User user){
        return userRepository.save(user);
    }

    public void delete (User user){
        throw new UnsupportedOperationException("delete not supported");
    }
}
