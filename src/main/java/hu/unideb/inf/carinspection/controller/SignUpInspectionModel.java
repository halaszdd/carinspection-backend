package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.domain.Site;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpInspectionModel {
    private long carId;
    private long siteId;
    private LocalDate date;
}
