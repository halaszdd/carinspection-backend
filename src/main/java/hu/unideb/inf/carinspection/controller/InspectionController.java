package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.CarRepository;
import hu.unideb.inf.carinspection.data.InspectionRepository;
import hu.unideb.inf.carinspection.data.InspectorRepository;
import hu.unideb.inf.carinspection.data.SiteRepository;
import hu.unideb.inf.carinspection.domain.Car;
import hu.unideb.inf.carinspection.domain.Inspection;
import hu.unideb.inf.carinspection.domain.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
public class InspectionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionController.class);

    private final InspectionRepository inspectionRepository;

    private final CarRepository carRepository;

    private final SiteRepository siteRepository;

    private final InspectorRepository inspectorRepository;

    public InspectionController(CarRepository carRepository, InspectionRepository inspectionRepository, SiteRepository siteRepository, InspectorRepository inspectorRepository) {
        this.carRepository = carRepository;
        this.inspectionRepository = inspectionRepository;
        this.siteRepository = siteRepository;
        this.inspectorRepository = inspectorRepository;
    }

    @GetMapping("/api/inspection/details/{carId}")
    public List<InspectionDTO> getInspection(@PathVariable long carId, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        Car car = carRepository.findById(carId).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "entity not found"
        );});

        List<Inspection> inspections = inspectionRepository.findAllByCar(car);
        if(defaultUserDetails.isAdmin() || car.getOwner() != null && defaultUserDetails.getAppUser().getId() == car.getOwner().getId()){
            return inspections.stream().map(InspectionDTO::new).toList();
        }

        throw new AccessDeniedException("403 returned");
    }

    @PostMapping("/api/inspection/signup")
    public void signUpInspection(@RequestBody @Valid SignUpInspectionModel signUpInspectionModel, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        Car car = carRepository.findById(signUpInspectionModel.getCarId()).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "car not found"
        );});

        Site site = siteRepository.findById(signUpInspectionModel.getSiteId()).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "site not found"
        );});

        if(defaultUserDetails.isAdmin() || car.getOwner() != null && defaultUserDetails.getAppUser().getId() == car.getOwner().getId())
        {
            inspectionRepository.save(Inspection.builder()
                    .car(car)
                    .site(site)
                    .inspector(inspectorRepository.findByFirstName("No Inspector"))
                    .result("PENDING")
                    .build());
            LOGGER.info("Created inspection: {}",signUpInspectionModel);
            return;
        }

        throw new AccessDeniedException("403 returned");
    }

    @GetMapping("/api/inspection/all")
    public List<InspectionDTO> getAllUser(@AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        if(!defaultUserDetails.isAdmin()) {
            throw new AccessDeniedException("403 returned");
        }
        return inspectionRepository.findAll().stream().map(InspectionDTO::new).toList();
    }

    @PostMapping("/api/inspection/{inspectionId}/withdraw")
    @Transactional
    public InspectionDTO withdrawInspection(@AuthenticationPrincipal DefaultUserDetails defaultUserDetails, @PathVariable long inspectionId) {
        Inspection inspection = inspectionRepository.findById(inspectionId).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "inspection not found"
        );});

        if (inspection.getCar().getOwner().getId() != defaultUserDetails.getAppUser().getId()) {
            throw new AccessDeniedException("403 returned");
        }

        if(!("PENDING".equals(inspection.getResult()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        inspection.setResult("WITHDRAWN");
        LOGGER.info("Withdrawn inspection: {}",inspection);
        return new InspectionDTO(inspection);
    }

    @PutMapping("/api/inspection/modify/{inspectionId}")
    @Transactional
    public InspectionDTO modifyInspection(@RequestBody  @Valid ModifyInspectionModel modifyInspectionModel,
                                       @PathVariable long inspectionId,
                                       @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {

        if(!defaultUserDetails.isAdmin()) {
            throw new AccessDeniedException("403 returned");
        }

        Inspection inspection = inspectionRepository.findById(inspectionId).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "inspection not found"
        );});

        if (modifyInspectionModel.getCarId() != null) {
            inspection.setCar(carRepository.findById(modifyInspectionModel.getCarId()).orElseThrow(() -> {throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "car not found"
            );}));
        }

        if (modifyInspectionModel.getInspectorId() != null) {
            inspection.setInspector(inspectorRepository.findById(modifyInspectionModel.getInspectorId()).orElseThrow(() -> {throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "inspector not found"
            );}));
        }

        if (modifyInspectionModel.getSiteId() != null) {
            inspection.setSite(siteRepository.findById(modifyInspectionModel.getSiteId()).orElseThrow(() -> {throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "site not found"
            );}));
        }

        if (modifyInspectionModel.getDate() != null) {
            inspection.setDate(modifyInspectionModel.getDate());
        }

        if (modifyInspectionModel.getResult() != null) {
            inspection.setResult(modifyInspectionModel.getResult().toUpperCase());
        }

        if (modifyInspectionModel.getComment() != null) {
            inspection.setComment(modifyInspectionModel.getComment());
        }
        LOGGER.info("Modified inspection: {}",modifyInspectionModel);
        return new InspectionDTO(inspection);
    }
}
