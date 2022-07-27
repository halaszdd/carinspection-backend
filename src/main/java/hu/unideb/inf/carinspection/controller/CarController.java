package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.AppUserRepository;
import hu.unideb.inf.carinspection.data.CarRepository;
import hu.unideb.inf.carinspection.domain.Car;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("api/car/")
    public List<Car> getCars() {
        return carRepository.findAll();
    }
}
