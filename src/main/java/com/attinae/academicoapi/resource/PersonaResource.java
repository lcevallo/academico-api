package com.attinae.academicoapi.resource;

import com.attinae.academicoapi.domain.Persona;
import com.attinae.academicoapi.exception.ExceptionHandling;
import com.attinae.academicoapi.exception.domain.EmailExistException;
import com.attinae.academicoapi.exception.domain.PersonaExistsException;
import com.attinae.academicoapi.exception.domain.PersonaNotFoundException;
import com.attinae.academicoapi.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "/persona")
public class PersonaResource extends ExceptionHandling {
    private final PersonaService personaService;

    @Autowired
    public PersonaResource(PersonaService personaService) {
        this.personaService = personaService;
    }


    @PostMapping
    public ResponseEntity<Persona> registrar(@RequestBody @Valid Persona persona) throws PersonaNotFoundException, PersonaExistsException, EmailExistException {

        persona.setCreado(new Date() );
        Persona newPersona= this.personaService.registrar(persona);
        return new ResponseEntity<>(newPersona, OK);
    }

}
