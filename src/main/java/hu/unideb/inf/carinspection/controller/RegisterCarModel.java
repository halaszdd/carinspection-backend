package hu.unideb.inf.carinspection.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCarModel {
    private String plateNumber;
    private String vin;
    private LocalDate expirationDate;
}
