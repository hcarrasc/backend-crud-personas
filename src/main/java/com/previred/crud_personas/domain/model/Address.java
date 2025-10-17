package com.previred.crud_personas.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Address {

    @NotBlank(message = "street is mandatory")
    private String street;
    @NotBlank(message = "commune is mandatory")
    private String commune;
    @NotBlank(message = "region is mandatory")
    private String region;

}
