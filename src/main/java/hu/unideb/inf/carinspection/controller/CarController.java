package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.AppUserRepository;
import hu.unideb.inf.carinspection.data.CarRepository;
import hu.unideb.inf.carinspection.domain.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private final CarRepository carRepository;

    private final AppUserRepository appUserRepository;

    public CarController(CarRepository carRepository, AppUserRepository appUserRepository) {
        this.carRepository = carRepository;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/api/car/register")
    public void registerCar(@RequestBody @Valid RegisterCarModel registerCarModel, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails)
    {
        final var carBuilder =Car.builder();
        if (defaultUserDetails.isAdmin() && registerCarModel.getOwnerId() != null) {
            final var owner = appUserRepository.findById(registerCarModel.getOwnerId()).orElseThrow(() -> {throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No such user");});
            carBuilder.owner(owner);
        }
        else {
            final var owner = appUserRepository.findById(defaultUserDetails.getAppUser().getId()).get();
            carBuilder.owner(owner);
        }
        carRepository.save(carBuilder
                .plateNumber(registerCarModel.getPlateNumber())
                .vin(registerCarModel.getVin())
                .expirationDate(registerCarModel.getExpirationDate())
                .build());
        LOGGER.info("Registered car: {}",registerCarModel);
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

    @PutMapping("/api/car/modify/{carId}")
    @Transactional
    public CarDTO modifyCar(@RequestBody @Valid ModifyCarModel modifyCarModel,
                            @PathVariable long carId,
                            @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {

        if(!defaultUserDetails.isAdmin()) {
            throw new AccessDeniedException("403 returned");
        }

        Car car = carRepository.findById(carId).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "car not found");});

        if(modifyCarModel.getPlateNumber() != null) {
            car.setPlateNumber(modifyCarModel.getPlateNumber());
        }

        if(modifyCarModel.getVin() != null) {
            car.setVin(modifyCarModel.getVin());
        }

        if(modifyCarModel.getExpirationDate() != null) {
            car.setExpirationDate(modifyCarModel.getExpirationDate());
        }
        LOGGER.info("Modified car : {}",modifyCarModel);
        return new CarDTO(car);
    }
}
