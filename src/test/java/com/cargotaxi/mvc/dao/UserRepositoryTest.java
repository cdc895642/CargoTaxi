package com.cargotaxi.mvc.dao;

import com.cargotaxi.config.DatabaseConfig;
import com.cargotaxi.config.MvcConfig;
import com.cargotaxi.config.SecurityConfig;
import com.cargotaxi.config.WebConfig;
import com.cargotaxi.mvc.controller.form.FindCarDTO;
import com.cargotaxi.mvc.model.CarType;
import com.cargotaxi.mvc.model.Offer;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.model.UserCar;
import com.cargotaxi.mvc.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

@WebAppConfiguration

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {DatabaseConfig.class, MvcConfig.class, SecurityConfig
                .class, WebConfig.class})
@Transactional
public class UserRepositoryTest {
    EntitiesCreator creator;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserCarRepository userCarRepository;
    @Autowired
    CarTypeRepository carTypeRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    OfferRepository offerRepository;

    @Before
    public void fillDB() {
        if (creator != null) {
            return;
        }
        creator = new EntitiesCreator();
        creator.create();
        creator.getCarTypes().forEach(carTypeRepository::save);
        creator.getCars().forEach(carRepository::save);
        creator.getUsers().forEach(userRepository::save);
        creator.getUserCars().forEach(userCarRepository::save);
        creator.getOffers().forEach(offerRepository::save);
        List<User> users = userRepository.findAll();
        lazyOffersLoad(users);
        for (User user : users) {
            System.out.println(String.format("===user - %s===", user.getLogin
                    ()));
            for (UserCar userCar : user.getCars()) {
                System.out.println(String.format("car location - %s", userCar
                        .getLocation()));
                System.out.println(String.format("car type - %s", userCar
                        .getCar().getCarType().getType()));
                System.out.println(String.format("car capacity - %s", userCar
                        .getCar().getCapacity()));
                System.out.println(String.format("car load - %s", userCar
                        .getCar().getLoad()));
                userCar.getOffers().forEach(o -> System.out.println(String
                        .format("car price - %s", o.getPrice())));
            }
        }
    }

    @Test
    public void
    UserCarSpecification_CargoCarTypeAndLoadAndPricePredicates_ReturnUsers() {
        //Arrange
        final String PASSENGER_CARTYPE = "грузовая";
        int expectedCount = 0;
        final int MIN_LOAD = 101;
        final int MAX_LOAD = 104;
        final BigDecimal MIN_PRICE=BigDecimal.valueOf(7);
        final BigDecimal MAX_PRICE=BigDecimal.valueOf(8);
        List<User> users = userRepository.findAll();
        lazyOffersLoad(users);
        for (User user : users) {
            for (UserCar userCar : user.getCars()) {
                boolean find=false;
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
                        expectedCount++;
                        find=true;
                        break;
                    }
                }
                if (find){
                    break;
                }
            }
        }
        UserServiceImplTest userService = new UserServiceImplTest();
        FindCarDTO findCarDTO = new FindCarDTO();
        CarType carType = carTypeRepository.findByType(PASSENGER_CARTYPE);
        findCarDTO.setCarTypeId(carType.getId());
        findCarDTO.setMinLoad(MIN_LOAD);
        findCarDTO.setMaxLoad(MAX_LOAD);
        findCarDTO.setMinPrice(MIN_PRICE);
        findCarDTO.setMaxPrice(MAX_PRICE);

        //Act
        Specification<User> specification = userService
                .userCarSpecification(findCarDTO);
        List<User> result = userRepository.findAll(specification);

        //Assert
        assertEquals(expectedCount, result.size());
    }

    @Test
    public void
    UserCarSpecification_CargoCarTypeAndLoadPredicates_ReturnUsers() {
        //Arrange
        final String PASSENGER_CARTYPE = "грузовая";
        int expectedCount = 0;
        final int MIN_LOAD = 101;
        final int MAX_LOAD = 103;
        List<User> users = userRepository.findAll();
        lazyOffersLoad(users);
        for (User user : users) {
            for (UserCar userCar : user.getCars()) {
                if (userCar.getCar().getCarType().getType().equalsIgnoreCase
                        (PASSENGER_CARTYPE)) {
                    if (userCar.getCar().getLoad()>=MIN_LOAD && userCar
                            .getCar().getLoad()<=MAX_LOAD){
                        expectedCount++;
                        break;
                    }
                }
            }
        }
        UserServiceImplTest userService = new UserServiceImplTest();
        FindCarDTO findCarDTO = new FindCarDTO();
        CarType carType = carTypeRepository.findByType(PASSENGER_CARTYPE);
        findCarDTO.setCarTypeId(carType.getId());
        findCarDTO.setMinLoad(MIN_LOAD);
        findCarDTO.setMaxLoad(MAX_LOAD);

        //Act
        Specification<User> specification = userService
                .userCarSpecification(findCarDTO);
        List<User> result = userRepository.findAll(specification);

        //Assert
        assertEquals(expectedCount, result.size());
    }

    @Test
    public void
    UserCarSpecification_PassengerCarTypePredicate_Return6Users() {
        //Arrange
        final String PASSENGER_CARTYPE = "легковая";
        final int EXPECTED_USER_COUNT = 6;
        UserServiceImplTest userService = new UserServiceImplTest();
        FindCarDTO findCarDTO = new FindCarDTO();
        CarType carType = carTypeRepository.findByType(PASSENGER_CARTYPE);
        findCarDTO.setCarTypeId(carType.getId());

        //Act
        Specification<User> specification = userService
                .userCarSpecification(findCarDTO);
        List<User> result = userRepository.findAll(specification);

        //Assert
        assertEquals(EXPECTED_USER_COUNT, result.size());
    }

    @Test
    public void UserCarSpecification_NoPredicate_ReturnAllUsers() {
        //Arrange
        UserServiceImplTest userService = new UserServiceImplTest();
        FindCarDTO findCarDTO = new FindCarDTO();
        List<User> expected = userRepository.findAll();
        findCarDTO.setCarTypeId(-1);

        //Act
        Specification<User> specification = userService
                .userCarSpecification(findCarDTO);
        List<User> result = userRepository.findAll(specification);

        //Assert
        assertEquals(expected.size(), result.size());
    }

    private void lazyOffersLoad(List<User> users) {
        users.forEach(
                user -> user.getCars().forEach(
                        userCar -> userCar.setOffers(
                                offerRepository.findByUserCar(userCar)
                        )));
    }

    private static class UserServiceImplTest extends UserServiceImpl {
        public Specification<User> userCarSpecification(FindCarDTO findCarDTO) {
            return super.userCarSpecification(findCarDTO);
        }
    }
}
