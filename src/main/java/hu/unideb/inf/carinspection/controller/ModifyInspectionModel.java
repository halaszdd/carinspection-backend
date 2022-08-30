package hu.unideb.inf.carinspection.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyInspectionModel {
    private Long carId;
    private Long inspectorId;
    private Long siteId;
    private LocalDate date;
    private String result;
    private String comment;
}
