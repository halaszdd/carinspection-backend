package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.InspectorRepository;
import hu.unideb.inf.carinspection.data.SiteRepository;
import hu.unideb.inf.carinspection.domain.Inspector;
import hu.unideb.inf.carinspection.domain.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class InspectorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectorController.class);

    private final InspectorRepository inspectorRepository;
    private final SiteRepository siteRepository;

    public InspectorController(InspectorRepository inspectorRepository, SiteRepository siteRepository) {
        this.inspectorRepository = inspectorRepository;
        this.siteRepository = siteRepository;
    }

    @GetMapping("/api/inspector/all")
    public List<InspectorDTO> getAllInspectors(@AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        if(!defaultUserDetails.isAdmin()) {
            throw new AccessDeniedException("403 returned");
        }
        final var inspectors = inspectorRepository.findAll();
        System.out.println(inspectors);
        return inspectors.stream().map(InspectorDTO::new).toList();
    }

    @PostMapping("/api/inspector/register")
    @Transactional
    public void registerInspector(@RequestBody @Valid RegisterInspectorModel registerInspectorModel,
                                  @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        if(!defaultUserDetails.isAdmin()) {
            throw new AccessDeniedException("403 returned");
        }

        Site site = siteRepository.findById(registerInspectorModel.getSiteId()).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Site not found");});

        inspectorRepository.save(Inspector
                .builder()
                .lastName(registerInspectorModel.getLastName())
                .firstName(registerInspectorModel.getFirstName())
                .site(site)
                .build());
        LOGGER.info("Registered Inspector: {}",registerInspectorModel);
    }

    @PutMapping("/api/inspector/modify/{inspectorId}")
    @Transactional
    public InspectorDTO modifyInspector(@RequestBody ModifyInspectorModel modifyInspectorModel, @PathVariable long inspectorId,
                                        @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        if(!defaultUserDetails.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not Admin!");
        }

        Inspector inspector = inspectorRepository.findById(inspectorId).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Inspector not found");});

        if(modifyInspectorModel.getFirstName() !=  null) {
            inspector.setFirstName(modifyInspectorModel.getFirstName());
        }

        if(modifyInspectorModel.getLastName() != null) {
            inspector.setLastName(modifyInspectorModel.getLastName());
        }

        if (modifyInspectorModel.getSiteId() != null) {
            inspector.setSite(siteRepository.findById(modifyInspectorModel.getSiteId()).get());
        }

        return new InspectorDTO(inspector);
    }
}
