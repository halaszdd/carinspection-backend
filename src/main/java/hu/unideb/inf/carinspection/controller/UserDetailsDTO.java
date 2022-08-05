package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.domain.*;
import lombok.Builder;
import lombok.Value;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Value
public class UserDetailsDTO {

    private long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private List<CarDTO> cars;

    public UserDetailsDTO(AppUser appUser) {

        id = appUser.getId();
        username = appUser.getUsername();
        password = appUser.getPassword();
        firstname = appUser.getFirstname();
        lastname = appUser.getLastname();
        email = appUser.getEmail();
        cars = appUser.getCars().stream().map(CarDTO::new).toList();
    }

    @Value
    public static class CarDTO {

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
    @Value
    public static class InspectionDTO {

        private long id;
        private Inspector inspector;
        private Site site;
        private LocalDate date;
        private String result;
        private String comment;

        public InspectionDTO(Inspection inspection) {
            id = inspection.getId();
            inspector = inspection.getInspector();
            site = inspection.getSite();
            date = inspection.getDate();
            result = inspection.getResult();
            comment = inspection.getComment();
        }
    }
}
