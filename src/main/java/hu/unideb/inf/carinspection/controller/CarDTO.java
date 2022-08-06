package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.domain.Car;
import lombok.Value;

import java.time.LocalDate;

@Value
public class CarDTO {

    private long id;
    private String plateNumber;
    private String vin;
    private LocalDate expirationDate;

    public CarDTO(Car car) {
        id = car.getId();
        plateNumber = car.getPlateNumber();
        vin = car.getVin();
        expirationDate = car.getExpirationDate();
    }

}
