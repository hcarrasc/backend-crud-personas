package com.previred.crud_personas.domain.repository;

import com.previred.crud_personas.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository <Person, Long> {
    Optional<Person> findByRut(String rut);
}
