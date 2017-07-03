package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.controller.form.FindCarDTO;
import com.cargotaxi.mvc.controller.form.NewUserDTO;
import com.cargotaxi.mvc.dao.OfferRepository;
import com.cargotaxi.mvc.dao.UserRepository;
import com.cargotaxi.mvc.model.Offer;
import com.cargotaxi.mvc.model.Phone;
import com.cargotaxi.mvc.model.Role;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.model.UserCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements
        UserService<User> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private OfferRepository offerRepository;

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
        List<User> users=userRepository.findAll(isExecutor());
        offersLazyLoad(users);
        return users;
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
        //offers lazy load
        offersLazyLoad(users);
        cleanResultList(users, findCarDTO);
        return users;
    }

    private void offersLazyLoad(List<User> users) {
        users.forEach(
                user -> user.getCars().forEach(
                        userCar -> userCar.setOffers(
                                offerRepository.findByUserCar(userCar)
                        )));
    }

    protected Specification<User> userCarSpecification(FindCarDTO findCarDTO) {
        return (root, query, builder) -> {
            //Constructing list of parameters
            List<Predicate> predicates = new ArrayList<Predicate>();
            Join<User, UserCar> userCarJoin = root.join("cars");
            Join<UserCar, Offer> offerJoin = userCarJoin.join("offers");

            //Adding predicates in case of parameter not being null
            if (findCarDTO.getMinCapacity() != null) {
                predicates.add(builder.ge(userCarJoin.get("car").get
                        ("capacity"), findCarDTO.getMinCapacity()));
            }
            if (findCarDTO.getMaxCapacity() != null) {
                predicates.add(builder.le(userCarJoin.get("car").get
                        ("capacity"), findCarDTO.getMaxCapacity()));
            }
            if (findCarDTO.getCarTypeId() == -1) {
                //find all types
            } else {
                predicates.add(builder.equal(userCarJoin.get("car")
                        .get("carType")
                        .get("id"), findCarDTO.getCarTypeId()));
            }
            if (findCarDTO.getDislocation() != null && !findCarDTO
                    .getDislocation().isEmpty()) {
                predicates.add(builder.like(
                        builder.lower(userCarJoin.get("location")),
                        "%" + findCarDTO.getDislocation().toLowerCase() + "%"));
            }
            if (findCarDTO.getMinLoad() != null) {
                predicates.add(builder.ge(userCarJoin.get("car").get
                        ("load"), findCarDTO.getMinLoad()));
            }
            if (findCarDTO.getMaxLoad() != null) {
                predicates.add(builder.le(userCarJoin.get("car").get
                        ("load"), findCarDTO.getMaxLoad()));
            }
            if (findCarDTO.getMinPrice() != null) {
                predicates.add(builder.ge(offerJoin.get("price"), findCarDTO
                        .getMinPrice()));
            }
            if (findCarDTO.getMaxPrice() != null) {
                predicates.add(builder.le(offerJoin.get("price"), findCarDTO
                        .getMaxPrice()));
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
        if (findCarDTO.getCarTypeId() != -1) {
            cleanNotEqualCarTypeId(users, findCarDTO);
        }
        if (findCarDTO.getDislocation() != null && !findCarDTO
                .getDislocation().isEmpty()) {
            cleanOtherDislocation(users, findCarDTO);
        }
        if (findCarDTO.getMinLoad() != null) {
            cleanLessThanCarMinLoad(users, findCarDTO);
        }
        if (findCarDTO.getMaxLoad() != null) {
            cleanMoreThanCarMaxLoad(users, findCarDTO);
        }
        if (findCarDTO.getMinPrice() != null) {
            cleanLessThanMinPrice(users, findCarDTO);
        }
        if (findCarDTO.getMaxPrice() != null) {
            cleanMoreThanMaxPrice(users, findCarDTO);
        }
    }

    private void cleanMoreThanMaxPrice(List<User> users, FindCarDTO findCarDTO) {
        java.util.function.Predicate<Offer> predicate = p -> p.getPrice()
                .compareTo(findCarDTO.getMaxPrice()) > 0;
        //remove inappropriate offers
        users.stream().forEach(user -> user.getCars().forEach(car -> car
                .getOffers().removeIf(predicate)));
        //remove cars where set of offers is empty
        users.stream().forEach(u -> u.getCars().removeIf(p -> p.getOffers()
                .size() == 0 || p.getOffers() == null));
    }

    private void cleanLessThanMinPrice(List<User> users, FindCarDTO
            findCarDTO) {
        java.util.function.Predicate<Offer> predicate = p -> p.getPrice()
                .compareTo(findCarDTO.getMinPrice()) < 0;
        //remove inappropriate offers
        users.stream().forEach(user -> user.getCars().forEach(car -> car
                .getOffers().removeIf(predicate)));
        //remove cars where set of offers is empty
        users.stream().forEach(u -> u.getCars().removeIf(p -> p.getOffers()
                .size() == 0 || p.getOffers() == null));
    }

    private void cleanMoreThanCarMaxLoad(List<User> users, FindCarDTO
            findCarDTO) {
        java.util.function.Predicate<UserCar> predicate = p -> p.getCar()
                .getLoad().compareTo
                        (findCarDTO.getMaxLoad()) > 0;
        users.stream().forEach(u -> u.getCars().removeIf(predicate));
    }

    private void cleanLessThanCarMinLoad(List<User> users, FindCarDTO
            findCarDTO) {
        java.util.function.Predicate<UserCar> predicate = p -> p.getCar()
                .getLoad().compareTo(findCarDTO.getMinLoad
                        ()) < 0;
        users.stream().forEach(u -> u.getCars().removeIf(predicate));
    }

    private void cleanOtherDislocation(List<User> users, FindCarDTO
            findCarDTO) {
        java.util.function.Predicate<UserCar> predicate = p -> !p.getLocation
                ().toLowerCase().contains(findCarDTO.getDislocation()
                .toLowerCase());
        users.stream().forEach(u -> u.getCars().removeIf(predicate));
    }

    private void cleanNotEqualCarTypeId(List<User> users, FindCarDTO
            findCarDTO) {
        java.util.function.Predicate<UserCar> predicate = p -> p.getCar()
                .getCarType().getId() != findCarDTO.getCarTypeId();
        users.stream().forEach(u -> u.getCars().removeIf(predicate));
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
