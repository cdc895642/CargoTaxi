package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.controller.form.FindCarDTO;
import com.cargotaxi.mvc.dao.EntitiesCreator;
import com.cargotaxi.mvc.dao.OfferRepository;
import com.cargotaxi.mvc.dao.UserRepository;
import com.cargotaxi.mvc.model.CarType;
import com.cargotaxi.mvc.model.Offer;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.model.UserCar;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Test
    public void findExecutorsBySpecification_setPredicate_returnResult() {
        //Arrange
        final String PASSENGER_CARTYPE = "грузовая";
        int expectedOfferCount = 0;
        int[] resultOfferCount = {0};
        final int MIN_LOAD = 101;
        final int MAX_LOAD = 104;
        final BigDecimal MIN_PRICE = BigDecimal.valueOf(7);
        final BigDecimal MAX_PRICE = BigDecimal.valueOf(8);
        EntitiesCreator creator = new EntitiesCreator();
        creator.create();
        List<User> usersAll = creator.getUsers();
        List<Offer> offersAll = creator.getOffers();
        lazyOfferLoad(usersAll,offersAll);
        for (User user : usersAll) {
            for (UserCar userCar : user.getCars()) {
                if (!userCar.getCar().getCarType().getType().equalsIgnoreCase
                        (PASSENGER_CARTYPE)) {
                    continue;
                }
                if (!(userCar.getCar().getLoad()>=MIN_LOAD && userCar
                        .getCar().getLoad()<=MAX_LOAD)){
                    continue;
                }
                for (Offer offer:userCar.getOffers()){
                    if (offer.getPrice().compareTo(MIN_PRICE)>=0 && offer
                            .getPrice().compareTo(MAX_PRICE)<=0){
                        expectedOfferCount++;
                    }
                }
            }
        }
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll(any(Specification.class)))
                .thenReturn(usersAll);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        RoleServiceImpl roleService = mock(RoleServiceImpl.class);
        OfferRepository offerRepository = mock(OfferRepository.class);

        UserServiceImpl userService = spy(new UserServiceImpl(userRepository,
                passwordEncoder, roleService, offerRepository));
        doNothing().when(userService).offersLazyLoad(anyList());
        FindCarDTO findCarDTO = new FindCarDTO();
        CarType carType = creator.getCarTypes().stream().filter(c->c.getType
                ().equalsIgnoreCase(PASSENGER_CARTYPE)).findFirst().get();
        findCarDTO.setCarTypeId(carType.getId());
        findCarDTO.setMinLoad(MIN_LOAD);
        findCarDTO.setMaxLoad(MAX_LOAD);
        findCarDTO.setMinPrice(MIN_PRICE);
        findCarDTO.setMaxPrice(MAX_PRICE);

        //Act
        List<User> users = userService.findExecutorsBySpecification(findCarDTO);
//        lazyOfferLoad(users,offersAll);
        users.forEach(user->{
            user.getCars().forEach(car->{
                car.getOffers().forEach(o->{
                    if (o.getPrice().compareTo(MIN_PRICE)>=0 &&
                            o.getPrice().compareTo(MAX_PRICE)<=0){
                        resultOfferCount[0]++;
                    }
                } );
            });
        });

        //Assert
        assertEquals(expectedOfferCount,resultOfferCount[0]);
    }

    private void lazyOfferLoad(List<User> usersAll, List<Offer> offersAll){
        for (User user : usersAll) {
            user.getCars().forEach(car -> {
                List<Offer> offers = offersAll.stream().filter(o -> o
                        .getUserCar().equals
                                (car)).collect(Collectors.toList());
                if (car.getOffers() == null) {
                    car.setOffers(new HashSet<>());
                }
                offers.forEach(car.getOffers()::add);
            });
        }
    }

}
