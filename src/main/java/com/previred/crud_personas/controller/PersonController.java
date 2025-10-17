package com.previred.crud_personas.controller;

import com.previred.crud_personas.domain.model.Person;
import com.previred.crud_personas.infrastructure.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/previred")
public class PersonController {

    private final PersonService personService;
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person")
    public List<Person> findAll(){
        return personService.findAll();
    }

    @PostMapping("/person")
    public ResponseEntity<Object> save(@RequestBody Person person){
        return personService.createNewPerson(person);
    }


}
