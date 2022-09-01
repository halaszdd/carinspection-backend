package hu.unideb.inf.carinspection.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterInspectorModel {

    private long siteId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
