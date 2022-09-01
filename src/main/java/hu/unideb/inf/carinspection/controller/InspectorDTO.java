package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.domain.Inspector;
import lombok.Value;

@Value
public class InspectorDTO {

    private long id;
    private long siteId;
    private String firstName;
    private String lastName;

    public InspectorDTO(Inspector inspector) {
        id = inspector.getId();
        siteId = inspector.getSite().getId();
        firstName = inspector.getFirstName();
        lastName = inspector.getLastName();
    }
}
