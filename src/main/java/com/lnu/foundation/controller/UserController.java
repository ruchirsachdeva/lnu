package com.lnu.foundation.controller;

import com.lnu.foundation.model.Car;
import com.lnu.foundation.model.Data;
import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.CarRepository;
import com.lnu.foundation.repository.UserRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.lnu.foundation.repository.DataRepository.loadData;

/**
 * Created by rucsac on 10/10/2018.
 */
@RestController
public class UserController {
    private UserRepository repository;
    private CarRepository carRepository;

    public UserController(CarRepository carRepository, UserRepository repository) {
        this.carRepository = carRepository;
        this.repository = repository;
    }

    @GetMapping("/cool-cars")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Car> coolUsers() {
        return carRepository.findAll().stream()
                .filter(this::isCool)
                .collect(Collectors.toList());
    }


    @GetMapping("/get-users")
    public Collection<User> users() {
        return repository.findAll().stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/get-data")
    public Collection<Data> data() {
        return loadData("data2").stream()
                .collect(Collectors.toList());
    }


    private boolean isCool(Car car) {
        return !car.getName().equals("AMC Gremlin") &&
                !car.getName().equals("Triumph Stag") &&
                !car.getName().equals("Ford Pinto") &&
                !car.getName().equals("Yugo GV");
    }
}
