package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.SiteRepository;
import hu.unideb.inf.carinspection.domain.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SiteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteController.class);

    private final SiteRepository siteRepository;

    public SiteController(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @GetMapping("/api/site/all")
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    @PostMapping("/api/site/register")
    @Transactional
    public void registerSite(@RequestBody @Valid RegisterSiteModel registerSiteModel, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        if(!defaultUserDetails.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not Admin!");
        }
        siteRepository.save(Site.builder().name(registerSiteModel.getName()).build());
        LOGGER.info("Registered user: {}",registerSiteModel);
    }
}
