package hu.unideb.inf.carinspection.controller;


import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.AppUserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
