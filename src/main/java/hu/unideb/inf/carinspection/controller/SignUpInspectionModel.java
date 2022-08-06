package hu.unideb.inf.carinspection.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpInspectionModel {
    @NotNull
    private long carId;
    @NotNull
    private long siteId;
    @NotNull
    private LocalDate date;
}
