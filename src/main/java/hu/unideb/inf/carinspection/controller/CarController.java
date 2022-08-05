package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.AppUserRepository;
import hu.unideb.inf.carinspection.data.CarRepository;
import hu.unideb.inf.carinspection.domain.Car;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class CarController {

    private final CarRepository carRepository;

    private final AppUserRepository appUserRepository;

    public CarController(CarRepository carRepository, AppUserRepository appUserRepository) {
        this.carRepository = carRepository;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/api/car/register/")
    public void registerCar(@RequestBody RegisterCarModel registerCarModel, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails)
    {
        System.out.println(registerCarModel);
        carRepository.save(Car.builder()
                .owner(appUserRepository.findById(defaultUserDetails.getAppUser().getId()).get())
                .plateNumber(registerCarModel.getPlateNumber())
                .vin(registerCarModel.getVin())
                .expirationDate(registerCarModel.getExpirationDate())
                .build());
    }

    @GetMapping("/api/car/{carId}")
    public UserDetailsDTO.CarDTO getCar(@PathVariable long carId, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        Car car = carRepository.findById(carId).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "entity not found"
        );});

        //Todo Admin impl
        if (car.getOwner()!=null && defaultUserDetails.getAppUser().getId() == car.getOwner().getId()) {
            return new UserDetailsDTO.CarDTO(car);
        }

        throw new AccessDeniedException("403 returned");
    }
}
