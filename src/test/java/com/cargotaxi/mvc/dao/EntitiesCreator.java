package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.Car;
import com.cargotaxi.mvc.model.CarType;
import com.cargotaxi.mvc.model.Offer;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.model.UserCar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntitiesCreator {

    private List<CarType> carTypes;
    private List<User> users;
    private List<Car> cars;
    private List<UserCar> userCars;
    private List<Offer> offers;

    public void create() {
        createCarTypes();
        createUsers();
        createCars();
        createUserCars();
        createOffers();
    }

    private void createOffers() {
        offers = new ArrayList<>();
        for (int x = 0; x < 24; x++) {
            Offer offer = new Offer();
            offer.setPrice(BigDecimal.valueOf(x % 6 + 1 + x / 6));
            offer.setDescription(String.format("description %s for price %s", x,
                    (x % 6 + 1 + x / 6)));
            offer.setUserCar(userCars.get(x % 12));
            offers.add(offer);
        }
    }

    private void createUserCars() {
        userCars = new ArrayList<>();
        int carIndex = 0;
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 2; y++) {
                UserCar userCar = new UserCar();
                userCar.setUser(users.get(x));
                users.get(x).getCars().add(userCar);
                userCar.setCar(cars.get(carIndex++));
                userCar.setDescription(String.format("description for car %s "
                        + "of "
                        + "user %s", y, x));
                userCar.setLocation(String.format("location for car %s of "
                        + "user %s", y, x));
                userCars.add(userCar);
            }
        }
    }

    private void createCars() {
        cars = new ArrayList<>();
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 2; y++) {
                Car car = new Car();
                car.setCarType(carTypes.get(y));
                car.setCapacity(BigDecimal.valueOf(x + y + 1));
                car.setLoad(x + y + 100);
                cars.add(car);
            }
        }
    }

    private void createCarTypes() {
        carTypes = new ArrayList<>();
        List<String> types = Arrays.asList(new String[]{"легковая",
                "грузовая"});
        types.forEach(t -> carTypes.add(new CarType(t)));
    }

    private void createUsers() {
        users = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            User user = new User();
            user.setEmail(String.format("user%s@gmail.com", i));
            user.setPassword(String.format("user%s", i));
            user.setLogin(String.format("user%s", i));
            users.add(user);
        }
    }

    public List<CarType> getCarTypes() {
        return carTypes;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<UserCar> getUserCars() {
        return userCars;
    }

    public List<Offer> getOffers() {
        return offers;
    }
}
