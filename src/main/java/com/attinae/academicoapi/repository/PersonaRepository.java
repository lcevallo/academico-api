package com.attinae.academicoapi.repository;

import com.attinae.academicoapi.domain.Persona;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
   Optional<Persona> findPersonaByEmailpersonal(String emailpersonal);
   Optional<Persona> findPersonaByPersonaId(Integer personaId);

    Optional<Persona> findPersonaByDocumentoidentificacion(String documentoidentificacion);
}