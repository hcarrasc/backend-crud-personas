package com.previred.crud_personas.api.dto;

import com.previred.crud_personas.domain.model.Address;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PersonRequest {

    @NotBlank
    @Pattern(regexp = "\\d{7,8}-[\\dkK]")
    private String rut;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotNull
    @Past
    private LocalDate birthdate;

    @NotNull
    private Address address;

}
