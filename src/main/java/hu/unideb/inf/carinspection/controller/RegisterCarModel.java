package hu.unideb.inf.carinspection.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCarModel {
    @NotNull
    private String plateNumber;
    @NotNull
    private String vin;
    @NotNull
    private LocalDate expirationDate;
}
