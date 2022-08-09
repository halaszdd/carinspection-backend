package hu.unideb.inf.carinspection.controller;


import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.AppUserRepository;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


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
}
