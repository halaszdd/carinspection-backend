package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.data.SiteRepository;
import hu.unideb.inf.carinspection.domain.Site;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SiteController {

    private final SiteRepository siteRepository;

    public SiteController(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @GetMapping("/api/site/all")
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }
}
