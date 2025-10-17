package com.previred.crud_personas.infrastructure.service;

import com.previred.crud_personas.domain.model.Person;
import com.previred.crud_personas.domain.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    HashMap<String, Object> responseData;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll (){
        return personRepository.findAll();
    }

    @Transactional
    public ResponseEntity<Object> createNewPerson(Person person) {

        responseData = new HashMap<>();
        Optional<Person> personFromRepo = personRepository.findByRut(person.getRut());

        if(personFromRepo.isPresent()){
            responseData.put("error", "user already exists");
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }

        Person newPerson = personRepository.save(person);
        responseData.put("person", newPerson);
        responseData.put("message", "person created successfully");

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }
}
