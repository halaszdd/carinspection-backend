package hu.unideb.inf.carinspection.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCarModel {

    private Long OwnerId;
    @NotNull
    private String plateNumber;
    @NotNull
    @Size(min = 17, max = 17)
    private String vin;
    @NotNull
    private LocalDate expirationDate;
}
