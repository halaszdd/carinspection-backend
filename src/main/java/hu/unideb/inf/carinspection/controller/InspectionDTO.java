package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.domain.Inspection;
import hu.unideb.inf.carinspection.domain.Inspector;
import hu.unideb.inf.carinspection.domain.Site;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class InspectionDTO {

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
