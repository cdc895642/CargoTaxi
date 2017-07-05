package com.cargotaxi.mvc.dao;

import com.cargotaxi.config.DatabaseConfig;
import com.cargotaxi.config.MvcConfig;
import com.cargotaxi.config.SecurityConfig;
import com.cargotaxi.config.WebConfig;
import com.cargotaxi.mvc.controller.form.FindCarDTO;
import com.cargotaxi.mvc.model.Car;
import com.cargotaxi.mvc.model.CarType;
import com.cargotaxi.mvc.model.Offer;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.model.UserCar;
import com.cargotaxi.mvc.service.UserService;
import com.cargotaxi.mvc.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextBootstrapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@WebAppConfiguration

@RunWith(SpringRunner.class)//SpringJUnit4ClassRunner.class
//@BootstrapWith(value=TestContextBootstrapper.class)

@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {DatabaseConfig.class, MvcConfig.class, SecurityConfig
                .class, WebConfig.class})
//, loader = AnnotationConfigContextLoader.class
@Transactional

//@Import(MvcConfig.class)
//@SpringBootTest
//@DataJpaTest
public class UserRepositoryTest {
    //    @Autowired
//    EntitiesCreator entitiesCreator;
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

    @Test
    public void test() {
        EntitiesCreator creator = new EntitiesCreator();
        UserServiceImplTest userService=new UserServiceImplTest();
        creator.create();
        List<CarType> carTypes=creator.getCarTypes().stream()
                .map(carTypeRepository::save)
                .collect(Collectors.toList());
        List<Car> cars=creator.getCars().stream().map(carRepository::save)
                .collect(Collectors.toList());;
        List<User> users=creator.getUsers().stream().map(userRepository::save).collect(Collectors.toList());
        System.out.println(users.size());
        List<UserCar> userCars=creator.getUserCars().stream().map
                (userCarRepository::save).collect(Collectors.toList());
        List<Offer> offers=creator.getOffers().stream().map
                (offerRepository::save).collect(Collectors.toList());
//        for (User user:creator.getUsers()){
//            Car car=null;
//            UserCar userCar1=null;
//            for (UserCar userCar:user.getCars()){
//                car=carRepository.save(userCar.getCar());
//                userCarRepository.save(userCar);
//            }
//        }
        FindCarDTO findCarDTO=new FindCarDTO();
        users=userRepository.findAll();
        System.out.println(users.size());
        findCarDTO.setCarTypeId(-1);
        Specification<User> spec=userService
                .userCarSpecification(findCarDTO);
        String s=spec.toString();
        //spec.toPredicate()
        List<User> result=userRepository.findAll(spec);
        users.forEach(
                user -> user.getCars().forEach(
                        userCar -> userCar.setOffers(
                                offerRepository.findByUserCar(userCar)
                        )));
        System.out.println(result.size());

    }

    private static class UserServiceImplTest extends UserServiceImpl{
        public Specification<User> userCarSpecification(FindCarDTO findCarDTO){
            return super.userCarSpecification(findCarDTO);
        }
    }
}
