package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.DefaultUserDetails;
import hu.unideb.inf.carinspection.data.AppUserRepository;
import hu.unideb.inf.carinspection.data.GroupRepository;
import hu.unideb.inf.carinspection.domain.AppUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class RegisterController {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final GroupRepository groupRepository;

    public RegisterController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, GroupRepository groupRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.groupRepository = groupRepository;
    }


    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterUserModel registerUserModel, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        if(defaultUserDetails != null && !defaultUserDetails.isAdmin()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already logged in!");
        }

        if(appUserRepository.existsByEmail(registerUserModel.getEmail()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already used!");
        }
        appUserRepository.save(AppUser
                .builder()
                .username(registerUserModel.getUsername())
                .password(passwordEncoder.encode(registerUserModel.getPassword()))
                .firstname(registerUserModel.getFirstname())
                .lastname(registerUserModel.getLastname())
                .email(registerUserModel.getEmail())
                .group(groupRepository.findByGroupName("customer"))
                .build());
        System.out.println(registerUserModel);
    }
}
