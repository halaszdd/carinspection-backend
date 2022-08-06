package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.domain.*;
import lombok.*;

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

}
