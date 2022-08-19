package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.AppUserRepository;
import hu.unideb.inf.carinspection.data.CarRepository;
import hu.unideb.inf.carinspection.domain.Car;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CarController {

    private final CarRepository carRepository;

    private final AppUserRepository appUserRepository;

    public CarController(CarRepository carRepository, AppUserRepository appUserRepository) {
        this.carRepository = carRepository;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/api/car/register")
    public void registerCar(@RequestBody @Valid RegisterCarModel registerCarModel, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails)
    {
        carRepository.save(Car.builder()
                .owner(appUserRepository.findById(defaultUserDetails.getAppUser().getId()).get())
                .plateNumber(registerCarModel.getPlateNumber())
                .vin(registerCarModel.getVin())
                .expirationDate(registerCarModel.getExpirationDate())
                .build());
    }

    @GetMapping("/api/car/{carId}")
    public CarDTO getCar(@PathVariable long carId, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        Car car = carRepository.findById(carId).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "entity not found"
        );});

        if (defaultUserDetails.isAdmin() || car.getOwner()!=null && defaultUserDetails.getAppUser().getId() == car.getOwner().getId()) {
            return new CarDTO(car);
        }

        throw new AccessDeniedException("403 returned");
    }

    @GetMapping("/api/car/all")
    public List<CarDTO> getAllUser(@AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        if(!defaultUserDetails.isAdmin()) {
            throw new AccessDeniedException("403 returned");
        }
        return carRepository.findAll().stream().map(CarDTO::new).toList();
    }
}
