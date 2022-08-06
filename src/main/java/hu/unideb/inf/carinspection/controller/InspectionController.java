package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.CarRepository;
import hu.unideb.inf.carinspection.data.InspectionRepository;
import hu.unideb.inf.carinspection.data.InspectorRepository;
import hu.unideb.inf.carinspection.data.SiteRepository;
import hu.unideb.inf.carinspection.domain.Car;
import hu.unideb.inf.carinspection.domain.Inspection;
import hu.unideb.inf.carinspection.domain.Site;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class InspectionController {

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
    public List<UserDetailsDTO.InspectionDTO> getInspection(@PathVariable long carId, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        Car car = carRepository.findById(carId).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "entity not found"
        );});

        List<Inspection> inspections = inspectionRepository.findAllByCar(car);
        if(car.getOwner() != null && defaultUserDetails.getAppUser().getId() == car.getOwner().getId()){
            return inspections.stream().map(UserDetailsDTO.InspectionDTO::new).toList();
        }

        throw new AccessDeniedException("403 returned");
    }

    @PostMapping("/api/inspection/signup/")
    public void signUpInspection(@RequestBody SignUpInspectionModel signUpInspectionModel, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        System.out.println(signUpInspectionModel);
        Car car = carRepository.findById(signUpInspectionModel.getCarId()).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "car not found"
        );});

        Site site = siteRepository.findById(signUpInspectionModel.getSiteId()).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "site not found"
        );});

        if(car.getOwner() != null && defaultUserDetails.getAppUser().getId() == car.getOwner().getId())
        {
            System.out.println(signUpInspectionModel);
            inspectionRepository.save(Inspection.builder()
                    .car(car)
                    .site(site)
                    .date(signUpInspectionModel.getDate())
                    .build());
            return;
        }

        throw new AccessDeniedException("403 returned");
    }

    @PutMapping("/api/inspection/modify/{inspectionId}")
    @Transactional
    public Inspection modifyInspection(@RequestBody ModifyInspectionModel modifyInspectionModel,
                                       @PathVariable long inspectionId,
                                       @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        //Todo roles admin impl
        Inspection inspection = inspectionRepository.findById(inspectionId).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "inspection not found"
        );});

        if (modifyInspectionModel.carId != null) {
            inspection.setCar(carRepository.findById(modifyInspectionModel.getCarId()).orElseThrow(() -> {throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "car not found"
            );}));
        }

        if (modifyInspectionModel.inspectorId != null) {
            inspection.setInspector(inspectorRepository.findById(modifyInspectionModel.getInspectorId()).orElseThrow(() -> {throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "inspector not found"
            );}));
        }

        if (modifyInspectionModel.siteId != null) {
            inspection.setSite(siteRepository.findById(modifyInspectionModel.getSiteId()).orElseThrow(() -> {throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "site not found"
            );}));
        }

        if (modifyInspectionModel.date != null) {
            inspection.setDate(modifyInspectionModel.getDate());
        }

        if (modifyInspectionModel.result != null) {
            inspection.setResult(modifyInspectionModel.getResult());
        }

        if (modifyInspectionModel.comment != null) {
            inspection.setComment(modifyInspectionModel.getComment());
        }
        return null;
    }
}
