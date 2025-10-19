package com.previred.crud_personas.application.service;

import com.previred.crud_personas.api.exception.PersonAlreadyExistsException;
import com.previred.crud_personas.api.exception.PersonNotFoundException;
import com.previred.crud_personas.domain.model.Address;
import com.previred.crud_personas.domain.model.Person;
import com.previred.crud_personas.domain.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonaServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void findAll_shouldReturnListOfPersons_whenPersonsExist() {

        Address address = new Address("street 123","Concepcion","Bio-Bio");
        Person person1 = new Person(null, "12345678-9", "Hector", "Carrasco",
                LocalDate.of(1990, 5, 20), address);
        Person person2 = new Person(null, "12345678-1", "Pepe", "Soto",
                LocalDate.of(1990, 5, 20), address);

        when(personRepository.findAll()).thenReturn(Arrays.asList(person1, person2));

        List<Person> result = personService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Hector", result.get(0).getFirstname());
        assertEquals("Pepe", result.get(1).getFirstname());

        verify(personRepository).findAll();
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoPersonsExist() {
        when(personRepository.findAll()).thenReturn(Collections.emptyList());

        List<Person> result = personService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(personRepository).findAll();
    }

    @Test
    void find_shouldReturnPerson_whenRutExists() {

        Address address = new Address("street 123","Concepcion","Bio-Bio");
        Person person = new Person(null, "12345678-9", "Héctor", "Carrasco",
                LocalDate.of(1990, 5, 20), address);

        when(personRepository.existsByRut(person.getRut())).thenReturn(true);
        when(personRepository.findByRut(person.getRut())).thenReturn(person);

        Person result = personService.find(person.getRut());

        assertNotNull(result);
        assertEquals("Héctor", result.getFirstname());
        verify(personRepository).existsByRut(person.getRut());
        verify(personRepository).findByRut(person.getRut());
    }

    @Test
    void find_shouldThrowException_whenRutDoesNotExist() {
        String rut = "98765432-1";
        when(personRepository.existsByRut(rut)).thenReturn(false);

        assertThrows(PersonNotFoundException.class, () -> personService.find(rut));
        verify(personRepository).existsByRut(rut);
        verify(personRepository, never()).findByRut(anyString());
    }


    @Test
    void createNewPerson_shouldSavePerson_whenRutDoesNotExist() {
        Person person = new Person();
        person.setRut("12345678-9");

        when(personRepository.existsByRut(person.getRut())).thenReturn(false);
        when(personRepository.save(person)).thenReturn(person);

        Person result = personService.createNewPerson(person);

        assertNotNull(result);
        verify(personRepository).existsByRut(person.getRut());
        verify(personRepository).save(person);
    }

    @Test
    void createNewPerson_shouldThrowException_whenRutAlreadyExists() {
        Person person = new Person();
        person.setRut("12345678-9");

        when(personRepository.existsByRut(person.getRut())).thenReturn(true);

        assertThrows(PersonAlreadyExistsException.class, () -> personService.createNewPerson(person));
        verify(personRepository).existsByRut(person.getRut());
        verify(personRepository, never()).save(any());
    }

    @Test
    void updatePerson_shouldSavePerson_whenRutExists() {
        Person person = new Person();
        person.setRut("12345678-9");

        when(personRepository.existsByRut(person.getRut())).thenReturn(true);
        when(personRepository.save(person)).thenReturn(person);

        Person result = personService.updatePerson(person);

        assertNotNull(result);
        verify(personRepository).existsByRut(person.getRut());
        verify(personRepository).save(person);
    }

    @Test
    void updatePerson_shouldThrowException_whenRutDoesNotExist() {
        // Arrange
        Person person = new Person();
        person.setRut("12345678-9");

        when(personRepository.existsByRut(person.getRut())).thenReturn(false);

        // Act & Assert
        assertThrows(PersonNotFoundException.class, () -> personService.updatePerson(person));
        verify(personRepository).existsByRut(person.getRut());
        verify(personRepository, never()).save(any());
    }

    @Test
    void delete_shouldDeletePerson_whenRutExists() {
        String rut = "12345678-9";
        Person person = new Person();
        person.setRut(rut);

        when(personRepository.existsByRut(rut)).thenReturn(true);
        when(personRepository.findByRut(rut)).thenReturn(person);

        personService.delete(rut);

        verify(personRepository).existsByRut(rut);
        verify(personRepository).findByRut(rut);
        verify(personRepository).delete(person);
    }

    @Test
    void delete_shouldThrowException_whenRutDoesNotExist() {
        String rut = "12345678-9";
        when(personRepository.existsByRut(rut)).thenReturn(false);

        assertThrows(PersonNotFoundException.class, () -> personService.delete(rut));

        verify(personRepository).existsByRut(rut);
        verify(personRepository, never()).findByRut(any());
        verify(personRepository, never()).delete(any());
    }

}
