package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.controller.form.FindCarDTO;
import com.cargotaxi.mvc.controller.form.NewUserDTO;
import com.cargotaxi.mvc.dao.UserRepository;
import com.cargotaxi.mvc.model.Car;
import com.cargotaxi.mvc.model.Phone;
import com.cargotaxi.mvc.model.Role;
import com.cargotaxi.mvc.model.Role_;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.model.UserCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cargotaxi.mvc.model.Role_.users;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements
        UserService<User> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleServiceImpl roleService;

    @PostConstruct
    public void init() {
        setRepository(userRepository);
    }

    @Transactional
    public User registerNewUser(NewUserDTO newUserDTO) {
        User user = new User();
        user.setLogin(newUserDTO.getLogin());
        user.setEmail(newUserDTO.getEmail());
        setPhones(newUserDTO, user);
        setRoles(user);
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        return userRepository.save(user);
    }

    private void setRoles(User user) {
        final String roleNameByDefault = "USER";
        Role role = roleService.findRoleByRoleName(roleNameByDefault);
        Set<Role> roles = null;
        if (role != null) {
            roles = new HashSet<>();
            roles.add(role);
        }
        user.setRoles(roles);
    }

    private void setPhones(NewUserDTO newUserDTO, User user) {
        Set<Phone> phones;
        if (newUserDTO.getPhones() != null
                || newUserDTO.getPhones().size() > 0) {
            phones = new HashSet<>();
            newUserDTO.getPhones().forEach(p -> phones.add(new Phone(p
                    .getNumber(), user)));
        } else {
            phones = null;
        }
        user.setPhones(phones);
    }

    public boolean isLoginExist(String login) {
        User user = userRepository.findByLogin(login);
        return user != null;
    }

    @Override
    public List<User> findAll(Specification<User> condition) {
        return userRepository.findAll(condition);
    }

    public List<User> findExecutorsAll() {
        return userRepository.findAll(isExecutor());
        //return userRepository.findExecutorAll();
    }

    @Transactional
    @Override
    public User findByLogin(String login) {
        User user = userRepository.findByLogin(login);
        //use for lazy init of roles
        user.getRoles().size();
        return user;
    }

    private Specification<User> isExecutor() {
        return (root, query, builder) -> {
            return builder.gt(builder.size(root.get("cars")), 0);
        };
    }

    @Override
    public List<User> findExecutorsBySpecification(FindCarDTO findCarDTO) {
        List<User> users = userRepository.findAll(userCarSpecification
                (findCarDTO));
        cleanResultList(users, findCarDTO);
        return users;
    }

    private Specification<User> userCarSpecification(FindCarDTO findCarDTO) {
        return (root, query, builder) -> {
            //Constructing list of parameters
            List<Predicate> predicates = new ArrayList<Predicate>();
            Join<User, UserCar> userCarJoin = root.join("cars");

            //Adding predicates in case of parameter not being null
            if (findCarDTO.getMinCapacity() != null) {
                predicates.add(builder.ge(userCarJoin.get("car").get
                        ("capacity"), findCarDTO.getMinCapacity()));
            }
            if (findCarDTO.getMaxCapacity() != null) {
                predicates.add(builder.le(userCarJoin.get("car").get
                        ("capacity"), findCarDTO.getMaxCapacity()));
            }
            if (findCarDTO.getCarTypeId() == 1) {
            }
            query.distinct(true);
            return builder.and(predicates.toArray(new Predicate[predicates
                    .size()]));
        };
    }

    private void cleanResultList(List<User> users, FindCarDTO findCarDTO) {
        if (findCarDTO.getMinCapacity() != null) {
            cleanLessThanCarMinCapacity(users, findCarDTO);
        }
        if (findCarDTO.getMaxCapacity() != null) {
            cleanMoreThanCarMaxCapacity(users, findCarDTO);
        }
    }

    private void cleanMoreThanCarMaxCapacity(List<User> users,
                                             FindCarDTO findCarDTO) {
        java.util.function.Predicate<UserCar> predicate = p -> p.getCar()
                .getCapacity().compareTo
                        (findCarDTO.getMaxCapacity()) > 0;
        users.stream().forEach(u -> u.getCars().removeIf(predicate));
    }

    private void cleanLessThanCarMinCapacity(List<User> users, FindCarDTO
            findCarDTO) {
        java.util.function.Predicate<UserCar> predicate = p -> p.getCar()
                .getCapacity().compareTo(findCarDTO.getMinCapacity
                        ()) < 0;
        users.stream().forEach(u -> u.getCars().removeIf(predicate));
    }


}
