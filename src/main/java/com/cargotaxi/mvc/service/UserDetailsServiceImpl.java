package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.model.UserPrincipalImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    public UserDetailsServiceImpl(){
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws
            UsernameNotFoundException {
        User user=userService.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        return new UserPrincipalImpl(user);
    }
}
