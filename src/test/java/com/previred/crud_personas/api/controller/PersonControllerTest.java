package com.previred.crud_personas.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.previred.crud_personas.application.service.PersonService;
import com.previred.crud_personas.domain.model.Address;
import com.previred.crud_personas.domain.model.Person;
import com.previred.crud_personas.api.dto.PersonRequest;
import com.previred.crud_personas.api.exception.GlobalExceptionHandler;
import com.previred.crud_personas.api.exception.PersonAlreadyExistsException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PersonControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // ObjectMapper para LocalDate
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // MockMvc standalone con manejador global de excepciones
        mockMvc = MockMvcBuilders.standaloneSetup(personController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void save_shouldReturn200_whenPersonIsValid() throws Exception {
        // Arrange
        PersonRequest request = new PersonRequest();
        request.setRut("12345678-9");
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setBirthdate(LocalDate.of(1990, 5, 20));
        request.setAddress(new Address("Calle Falsa 123", "Santiago", "Metropolitana"));

        Person savedPerson = new Person(1L, request.getRut(), request.getFirstname(),
                request.getLastname(), request.getBirthdate(), request.getAddress());

        when(personService.createNewPerson(any(Person.class))).thenReturn(savedPerson);

        // Act & Assert
        mockMvc.perform(post("/api/previred/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rut").value("12345678-9"))
                .andExpect(jsonPath("$.message").value("Person created successfully"));
    }

    @Test
    void save_shouldReturn400_whenInvalidFormat() throws Exception {
        PersonRequest request = new PersonRequest();
        request.setRut("12333222-2"); // formato inválido
        request.setFirstname("");      // @NotBlank viola
        request.setLastname("");       // @NotBlank viola
        request.setBirthdate(LocalDate.of(2030, 1, 1)); // @Past viola
        request.setAddress(new Address("", "", ""));  // campos vacíos

        mockMvc.perform(post("/api/previred/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void save_shouldReturn409_whenPersonAlreadyExists() throws Exception {
        // Arrange
        PersonRequest request = new PersonRequest();
        request.setRut("12345678-9");
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setBirthdate(LocalDate.of(1990, 5, 20));
        request.setAddress(new Address("Calle Falsa 123", "Santiago", "Metropolitana"));

        when(personService.createNewPerson(any(Person.class)))
                .thenThrow(new PersonAlreadyExistsException("Person already exists"));

        // Act & Assert
        mockMvc.perform(post("/api/previred/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Person already exists"));
    }
}