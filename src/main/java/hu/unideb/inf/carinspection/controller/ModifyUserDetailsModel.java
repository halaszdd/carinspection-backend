package hu.unideb.inf.carinspection.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyUserDetailsModel {

    public String firstname;
    public String lastname;
    public String email;
}
