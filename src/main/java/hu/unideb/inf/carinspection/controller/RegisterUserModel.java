package hu.unideb.inf.carinspection.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserModel {
    @NotNull
    @Size(min = 4)
    private String username;
    @NotNull
    @Size(min = 4)
    private String password;
    private String firstname;
    private String lastname;
    @NotNull
    @Pattern(regexp = ".*@.*")
    private String email;
}
