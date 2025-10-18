package com.previred.crud_personas.api.controller;

import com.previred.crud_personas.api.dto.PersonRequest;
import com.previred.crud_personas.api.dto.PersonResponse;
import com.previred.crud_personas.domain.model.Person;
import com.previred.crud_personas.application.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/previred/person")
public class PersonController {

    private final PersonService personService;
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(summary = "Get a list of persons",
            description = "Get full list of Persons in database")
    @ApiResponse(responseCode = "200", description = "request performed ok",
            content = @Content(schema = @Schema(implementation = PersonResponse.class)))
    @GetMapping
    public List<Person> findAll(){
        return personService.findAll();
    }

    @Operation(summary = "Get a person",
            description = "Get a person by given {rut} parameter")
    @ApiResponse(responseCode = "200", description = "Request performed ok",
            content = @Content(schema = @Schema(implementation = PersonResponse.class)))
    @ApiResponse(responseCode = "404", description = "Person not found exception",
            content = @Content(schema = @Schema()))
    @GetMapping("/{rut}")
    public Person find(@PathVariable String rut){
        return personService.find(rut);
    }

    @Operation(summary = "Save a person",
            description = "Save a person in database")
    @ApiResponse(responseCode = "200", description = "Request performed ok",
            content = @Content(schema = @Schema(implementation = PersonResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request, check input data",
            content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "409", description = "Person already exist exception",
            content = @Content(schema = @Schema()))
    @PostMapping
    public PersonResponse save(@Valid @RequestBody PersonRequest request){

        Person person = new Person(
                null,
                request.getRut(),
                request.getFirstname(),
                request.getLastname(),
                request.getBirthdate(),
                request.getAddress()
        );

        Person savedPerson = personService.createNewPerson(person);
        return new PersonResponse(
                savedPerson.getId(),
                savedPerson.getRut(),
                savedPerson.getFirstname(),
                savedPerson.getLastname(),
                savedPerson.getBirthdate(),
                savedPerson.getAddress(),
                "Person created successfully"
        );

    }

    @Operation(summary = "Update a person",
            description = "Update person data in database given the {rut} parameter in request")
    @ApiResponse(responseCode = "200", description = "Request performed ok",
            content = @Content(schema = @Schema(implementation = PersonResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request, check input data",
            content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "404", description = "Person not found exception",
            content = @Content(schema = @Schema()))
    @PatchMapping
    public PersonResponse update(@Valid @RequestBody PersonRequest request){

        Person person = new Person(
                null,
                request.getRut(),
                request.getFirstname(),
                request.getLastname(),
                request.getBirthdate(),
                request.getAddress()
        );

        Person savedPerson = personService.updatePerson(person);
        return new PersonResponse(
                savedPerson.getId(),
                savedPerson.getRut(),
                savedPerson.getFirstname(),
                savedPerson.getLastname(),
                savedPerson.getBirthdate(),
                savedPerson.getAddress(),
                "Person updated successfully"
        );
    }

    @Operation(summary = "Delete a person",
            description = "Delete a person from database by given {rut} parameter")
    @ApiResponse(responseCode = "200", description = "Request performed ok",
            content = @Content(schema = @Schema(implementation = PersonResponse.class)))
    @ApiResponse(responseCode = "404", description = "Person not found exception",
            content = @Content(schema = @Schema()))
    @DeleteMapping ("/{rut}")
    public void delete(@PathVariable String rut){
        personService.delete(rut);
    }

}
