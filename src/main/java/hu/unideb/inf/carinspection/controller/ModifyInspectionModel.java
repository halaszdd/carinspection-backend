package hu.unideb.inf.carinspection.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyInspectionModel {
    public Long carId;
    public Long inspectorId;
    public Long siteId;
    public LocalDate date;
    public String result;
    public String comment;
}
