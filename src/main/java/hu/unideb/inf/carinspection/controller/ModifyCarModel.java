package hu.unideb.inf.carinspection.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyCarModel {

    public String plateNumber;
    public String vin;
    public LocalDate expirationDate;
}
