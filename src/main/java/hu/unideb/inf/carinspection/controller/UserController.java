package hu.unideb.inf.carinspection.controller;


import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.AppUserRepository;


import hu.unideb.inf.carinspection.domain.AppUser;
import hu.unideb.inf.carinspection.domain.Car;
import hu.unideb.inf.carinspection.domain.Inspection;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final AppUserRepository appUserRepository;

    public UserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/api/user/details")
    public UserDetailsDTO getUserDetails(@AuthenticationPrincipal DefaultUserDetails defaultUserDetails)
    {
        final var appUser = defaultUserDetails.getAppUser();
        final var userId = appUser.getId();
        final var userFromRepo = appUserRepository.findById(userId);
        return new UserDetailsDTO(userFromRepo.get());
    }

    @GetMapping("/api/user/all")
    public List<UserDetailsDTO> getAllUser(@AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        if(!defaultUserDetails.isAdmin()) {
            throw new AccessDeniedException("403 returned");
        }
        return appUserRepository.findAll().stream().map(UserDetailsDTO::new).toList();
    }

    @GetMapping("/api/user/{userId}")
    public UserDetailsDTO getUserById(@PathVariable long userId, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        if(!defaultUserDetails.isAdmin()) {
            throw new AccessDeniedException("403 returned");
        }
        return new UserDetailsDTO(appUserRepository.findById(userId).orElseThrow(UserNotFoundException::new));
    }

    @GetMapping("/api/user/inspection")
    @Transactional
    public List<InspectionDTO> getUserInspection(@AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        AppUser appUser = appUserRepository.findById(defaultUserDetails.getAppUser().getId()).get();
        List<Car> car = appUser.getCars();
        List<Inspection> inspections = car.stream()
                .flatMap(carElement -> carElement.getInspections().stream())
                .toList();
        return inspections.stream().map(InspectionDTO::new).toList();
    }

    @PutMapping("/api/user/modify/{userId}")
    @Transactional
    public UserDetailsDTO modifyUser(@RequestBody @Valid ModifyUserDetailsModel modifyUserDetailsModel,
                                     @PathVariable long userId,
                                     @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {

        if(!defaultUserDetails.isAdmin()) {
            throw new AccessDeniedException("403 returned");
        }

        AppUser appUser = appUserRepository.findById(userId).orElseThrow(() -> {throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found");});

        if(modifyUserDetailsModel.firstname != null) {
            appUser.setFirstname(modifyUserDetailsModel.getFirstname());
        }

        if (modifyUserDetailsModel.lastname != null) {
            appUser.setLastname(modifyUserDetailsModel.getLastname());
        }

        if (modifyUserDetailsModel.email != null) {
            appUser.setEmail(modifyUserDetailsModel.getEmail());
        }
        return new UserDetailsDTO(appUser);
    }
}
