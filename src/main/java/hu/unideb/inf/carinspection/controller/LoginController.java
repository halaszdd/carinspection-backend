package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.DefaultUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public DefaultUserDetails getUser(@AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        System.out.println("loginlog");
        return defaultUserDetails;
    }

}
