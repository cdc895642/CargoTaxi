package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.controller.form.NewCarDTO;
import com.cargotaxi.mvc.controller.form.OfferDTO;
import com.cargotaxi.mvc.dao.OfferRepository;
import com.cargotaxi.mvc.dao.OrderRepository;
import com.cargotaxi.mvc.dao.UserCarRepository;
import com.cargotaxi.mvc.dao.UserRepository;
import com.cargotaxi.mvc.model.Car;
import com.cargotaxi.mvc.model.CarType;
import com.cargotaxi.mvc.model.Offer;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.model.UserCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.security.Principal;

@Service
public class UserCarServiceImpl  extends AbstractServiceImpl<UserCar> implements
        UserCarService<UserCar>{

    @Autowired
    UserCarRepository userCarRepository;
    @Autowired
    //use because of LazyLoadException (instead of UserService)
    UserRepository userRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    CarTypeService carTypeService;
    @Autowired
    CarService carService;

    @PostConstruct
    public void init() {
        setRepository(userCarRepository);
    }

    @Transactional
    @Override
    public UserCar createNewCar(NewCarDTO newCarDTO, Principal principal) {
        User user= userRepository.findByLogin(principal.getName());
        CarType carType=carTypeService.findById(newCarDTO
                .getCarTypeId());
        Car car=new Car();
        car.setLoad(newCarDTO.getLoad());
        car.setCapacity(newCarDTO.getCapacity());
        car.setCarType(carType);
        car=carService.save(car);
        if (car==null){
            return null;
        }
        UserCar userCar=new UserCar();
        userCar.setCar(car);
        userCar.setDescription(newCarDTO.getDescription());
        userCar.setLocation(newCarDTO.getLocation());
        userCar.setUser(user);
        userCar=userCarRepository.save(userCar);
        if (userCar==null){
            return null;
        }
        for (OfferDTO offerDTO:newCarDTO.getOffers()){
            Offer offer=new Offer();
            offer.setDescription(offerDTO.getDescription());
            offer.setPrice(offerDTO.getPrice());
            offer.setUserCar(userCar);
            offerRepository.save(offer);
        }
        return userCar;
    }
}
