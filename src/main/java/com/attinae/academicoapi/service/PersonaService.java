package com.attinae.academicoapi.service;


import com.attinae.academicoapi.domain.Persona;
import com.attinae.academicoapi.exception.domain.EmailExistException;
import com.attinae.academicoapi.exception.domain.PersonaExistsException;
import com.attinae.academicoapi.exception.domain.PersonaNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PersonaService {

    Persona registrar(String documentoIdentificacion,
                      String nombre1, String apellido1,
                      String emailPersonal,
                      Character activo, Integer creadoPor,
                      Date creado, Integer estadoId, Character tipoPersona, Date fechaNacimiento, String nombre2, String apellido2, String direccion,   String telefonoConvencional,   String telefonoCelular, Integer operadoraFk,   String emailInstitucional, int nacionalidadId, Date fechaIngreso, Integer tipoDocumentoId, Integer sexoId, Integer identidadGeneroId, Integer estadoCivilId, String tipoSangre, Integer tipoDiscapacidadId, Short porcentajeDiscapacidad,   String descripcionDiscapacidad, Integer idiomaId, Integer etniaId, Integer provinciaNacimientoId, Integer paisnacimientoId, Integer ciudadNacimientoId, Integer parroquiaNacimientoId,   String personaContacto, String telefonoContacto, Integer provinciaDomicilioId, Integer paisDomicilioId, Integer ciudadDomicilioId, Integer parroquiaDomicilioId,   String barrio,   String referenciaDireccion, String numeroCarnetDiscapacidad) throws PersonaNotFoundException, PersonaExistsException, EmailExistException;

    List<Persona> getPersonas();

    Optional<Persona> findPersonaByEmailpersonal(String emailpersonal);

    Optional<Persona> findPersonaByDocumentoidentificacion(String documentoidentificacion);

    Persona registrar(Persona persona) throws PersonaNotFoundException, PersonaExistsException, EmailExistException;

    Optional<Persona> findPersonaByPersonaId(Integer personaId);

    Persona addNewPersona(Persona persona);

    Persona updatePersona(Integer idPersona, Persona persona) throws PersonaNotFoundException, PersonaExistsException, EmailExistException;

    void deletePersona(Integer idPersona);


}