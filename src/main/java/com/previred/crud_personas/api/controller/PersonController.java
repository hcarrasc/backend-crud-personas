package com.previred.crud_personas.api.controller;

import com.previred.crud_personas.api.dto.PersonRequest;
import com.previred.crud_personas.api.dto.PersonResponse;
import com.previred.crud_personas.domain.model.Person;
import com.previred.crud_personas.application.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public List<Person> findAll(){
        return personService.findAll();
    }

    @GetMapping("/{rut}")
    public Person find(@PathVariable String rut){
        return personService.find(rut);
    }

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

    @DeleteMapping ("/{rut}")
    public void delete(@PathVariable String rut){
        personService.delete(rut);
    }

}
