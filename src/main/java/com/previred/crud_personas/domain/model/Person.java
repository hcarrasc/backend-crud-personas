package com.previred.crud_personas.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Table
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "RUT is mandatory")
    @Pattern(regexp = "\\d{7,8}-[\\dkK]", message = "RUT format invalid")
    private String rut;
    @NotBlank(message = "Firstname is mandatory")
    private String firstname;
    @NotBlank(message = "Lastname is mandatory")
    private String lastname;
    @NotNull(message = "Birthname is mandatory")
    @Past(message = "Birthname must be in the past")
    private LocalDate birthdate;

    @Embedded
    private Address address;
}
