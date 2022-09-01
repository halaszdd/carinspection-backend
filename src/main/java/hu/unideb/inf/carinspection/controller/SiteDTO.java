package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.domain.Site;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SiteDTO {

    private String name;

    public SiteDTO(Site site) {
        name = site.getName();
    }
}
