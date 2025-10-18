package com.previred.crud_personas.application.service;

import com.previred.crud_personas.api.exception.PersonAlreadyExistsException;
import com.previred.crud_personas.api.exception.PersonNotFoundException;
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

    public Person find(String rut) {
        if (!personRepository.existsByRut(rut)) {
            throw new PersonNotFoundException("Person not found");
        }
        return personRepository.findByRut(rut);
    }

    @Transactional
    public Person createNewPerson(Person person) {

        if (personRepository.existsByRut(person.getRut())) {
            throw new PersonAlreadyExistsException("Person already exists");
        }
        return personRepository.save(person);

    }

    @Transactional
    public Person updatePerson(Person person) {

        if (!personRepository.existsByRut(person.getRut())) {
            throw new PersonNotFoundException("Person not found");
        }
        return personRepository.save(person);

    }

    @Transactional
    public void delete(String rut) {

        if (!personRepository.existsByRut(rut)) {
            throw new PersonNotFoundException("Person with rut "+rut+" not found");
        }

        Person personFromRepo = personRepository.findByRut(rut);
        personRepository.delete(personFromRepo);

    }

}
