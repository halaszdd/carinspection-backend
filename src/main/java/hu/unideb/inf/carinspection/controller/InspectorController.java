package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.InspectorRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InspectorController {

    private final InspectorRepository inspectorRepository;

    public InspectorController(InspectorRepository inspectorRepository) {
        this.inspectorRepository = inspectorRepository;
    }

    @GetMapping("/api/inspector/all")
    public List<InspectorDTO> getAllInspectors(@AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        if(!defaultUserDetails.isAdmin()) {
            throw new AccessDeniedException("403 returned");
        }
        return inspectorRepository.findAll().stream().map(InspectorDTO::new).toList();
    }
}
