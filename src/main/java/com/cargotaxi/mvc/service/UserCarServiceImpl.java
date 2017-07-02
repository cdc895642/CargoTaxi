package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.controller.form.FindCarDTO;
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
import java.util.List;
import java.util.Optional;

@Service
public class UserCarServiceImpl extends AbstractServiceImpl<UserCar> implements
        UserCarService {

    @Autowired
    UserCarRepository userCarRepository;
    //use because of LazyLoadException (instead of UserService)
    @Autowired
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
        User user = userRepository.findByLogin(principal.getName());
        Car car = setCar(null, newCarDTO);
        if (car == null) {
            return null;
        }
        UserCar userCar = setUserCar(null, car, user, newCarDTO);
        if (userCar == null) {
            return null;
        }
        for (OfferDTO offerDTO : newCarDTO.getOffers()) {
            Offer offer = setOffer(null, userCar, offerDTO);
        }
        return userCar;
    }

    private Offer setOffer(Offer offer, UserCar userCar, OfferDTO offerDTO) {
        Offer newOffer = offer == null ? new Offer() : offer;
        newOffer.setDescription(offerDTO.getDescription());
        newOffer.setPrice(offerDTO.getPrice());
        if (userCar!=null) {
            newOffer.setUserCar(userCar);
        }
        offerRepository.save(newOffer);
        return newOffer;
    }

    private UserCar setUserCar(UserCar userCar, Car car, User user, NewCarDTO
            newCarDTO) {
        UserCar newUserCar = userCar == null ? new UserCar() : userCar;
        if (car!=null){
            newUserCar.setCar(car);
        }
        newUserCar.setDescription(newCarDTO.getDescription());
        newUserCar.setLocation(newCarDTO.getLocation());
        if (user!=null){
            newUserCar.setUser(user);
        }
        newUserCar = userCarRepository.save(newUserCar);
        return newUserCar;
    }

    private Car setCar(Car car, NewCarDTO newCarDTO) {
        Car newCar = car == null ? new Car() : car;
        CarType carType = carTypeService.findById(newCarDTO
                .getCarTypeId());
        newCar.setLoad(newCarDTO.getLoad());
        newCar.setCapacity(newCarDTO.getCapacity());
        newCar.setCarType(carType);
        newCar = carService.save(newCar);
        return newCar;
    }

    @Override
    public List<UserCar> findUserCarsOfPrincipal(Principal user) {
        List<UserCar> cars = userCarRepository.findByUserLogin(user.getName());
        cars.forEach(car -> car.setOffers(offerRepository.findByUserCar(car)));
        return cars;
    }

    @Transactional
    @Override
    public UserCar saveCarChange(NewCarDTO newCarDTO) {
        UserCar userCar = userCarRepository.findOne(newCarDTO.getUserCarId());
        userCar.setCar(setCar(userCar.getCar(), newCarDTO));
        userCar = setUserCar(userCar, null, null, newCarDTO);
        userCar.getOffers().forEach(offerRepository::delete);
        for (OfferDTO offerDTO:newCarDTO.getOffers()){
            Offer offer = setOffer(null, userCar, offerDTO);
        }
        userCar=findById(newCarDTO.getUserCarId());
        return userCar;
    }

    @Override
    public UserCar findById(int id) {
        UserCar car = userCarRepository.findOne(id);
        car.setOffers(offerRepository.findByUserCar(car));
        return car;
    }
}
