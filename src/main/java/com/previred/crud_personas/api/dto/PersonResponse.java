package com.previred.crud_personas.api.dto;

import com.previred.crud_personas.domain.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PersonResponse {

    private Long id;
    private String rut;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private Address address;
    private String message;

}