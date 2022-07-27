package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.data.AppUserRepository;
import hu.unideb.inf.carinspection.domain.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    public RegisterController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/register")
    public void register(@RequestBody RegisterUserModel registerUserModel) {
        appUserRepository.save(AppUser
                .builder()
                .username(registerUserModel.getUsername())
                .password(passwordEncoder.encode(registerUserModel.getPassword()))
                .email(registerUserModel.getEmail())
                .build());
        System.out.println(registerUserModel);
    }
}
