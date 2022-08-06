package hu.unideb.inf.carinspection.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserModel {
    @NotNull
    @Min(4)
    private String username;
    @NotNull
    @Min(4)
    private String password;
    @NotNull
    @Pattern(regexp = ".*@.*")
    private String email;
}
