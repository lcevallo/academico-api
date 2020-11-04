package com.attinae.academicoapi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.attinae.academicoapi.domain.Persona;
import com.attinae.academicoapi.exception.domain.EmailExistException;
import com.attinae.academicoapi.exception.domain.PersonaExistsException;
import com.attinae.academicoapi.exception.domain.PersonaNotFoundException;
import com.attinae.academicoapi.repository.PersonaRepository;
import com.attinae.academicoapi.service.PersonaService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Qualifier("PersonaService")
public class PersonaServiceImpl implements PersonaService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final PersonaRepository personaRepository;

    @Autowired
    public PersonaServiceImpl(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public Persona registrar(Persona persona) throws PersonaNotFoundException, PersonaExistsException, EmailExistException {
        validateDocumentoIdentificionAndEmail(StringUtils.EMPTY, persona.getDocumentoidentificacion(), persona.getEmailpersonal());

        Persona personaGuardada = personaRepository.save(persona);
        LOGGER.info("La nueva Persona tiene el siguiente id : "+personaGuardada.getPersonaId());

        return persona;
    }

    @Override
    public Optional<Persona> findPersonaByPersonaId(Integer personaId) {
        return this.personaRepository.findById(personaId);
    }

    @Override
    public Persona addNewPersona(Persona persona) {
        return null;
    }

    @Override
    public Persona updatePersona(Integer idPersona, Persona persona) throws PersonaNotFoundException, PersonaExistsException, EmailExistException {

        Optional<Persona> personaById = validarPersonaXMailxCedula(idPersona, persona);

        Persona personaTarget = personaById.get();
        BeanUtils.copyProperties(persona,personaTarget,"personaId");
        Persona personaGuardada = this.personaRepository.save(personaTarget);

        return personaGuardada;

    }


    /**
     * Este metodo solo se usa para actualizar ya que buscara por 3 campos si ya existe datos de personas que ya estan registradas en la base de datos
     * @param idPersona
     * @param persona
     * @return
     * @throws PersonaNotFoundException
     * @throws EmailExistException
     * @throws PersonaExistsException
     */
    private Optional<Persona> validarPersonaXMailxCedula(Integer idPersona, Persona persona) throws PersonaNotFoundException, EmailExistException, PersonaExistsException {
        Optional<Persona> personaById = this.personaRepository.findById(idPersona);

        if(!personaById.isPresent()){
            throw new PersonaNotFoundException("No existe persona con este id: " + idPersona + " para actualizar");
        }

        Optional<Persona> personaByEmailpersonal = findPersonaByEmailpersonal(persona.getEmailpersonal());

        if(personaByEmailpersonal.isPresent() && !(personaByEmailpersonal.get().getPersonaId().equals(personaById.get().getPersonaId())) )
        {
            throw  new EmailExistException("Ya existe ese email en otra persona");
        }

        Optional<Persona> personaByDocumentoidentificacion = findPersonaByDocumentoidentificacion(persona.getDocumentoidentificacion());

        if(personaByDocumentoidentificacion.isPresent() && !(personaByDocumentoidentificacion.get().getPersonaId().equals(personaById.get().getPersonaId())) )
        {
            throw  new PersonaExistsException("Ya existe una persona con esta Cedula o Pasaporte");
        }
        return personaById;
    }

    @Override
    public void deletePersona(Integer idPersona) {
            this.personaRepository.deleteById(idPersona);
    }

    @Override
    public Persona registrar(String documentoIdentificacion, String nombre1, String apellido1, String emailPersonal, Character activo, Integer creadoPor, Date creado, Integer estadoId, Character tipoPersona, Date fechaNacimiento, String nombre2,  String apellido2,  String direccion,  String telefonoConvencional,  String telefonoCelular, Integer operadoraFk,  String emailInstitucional, int nacionalidadId, Date fechaIngreso, Integer tipoDocumentoId, Integer sexoId, Integer identidadGeneroId, Integer estadoCivilId, String tipoSangre, Integer tipoDiscapacidadId, Short porcentajeDiscapacidad,  String descripcionDiscapacidad, Integer idiomaId, Integer etniaId, Integer provinciaNacimientoId, Integer paisnacimientoId, Integer ciudadNacimientoId, Integer parroquiaNacimientoId,  String personaContacto,  String telefonoContacto, Integer provinciaDomicilioId, Integer paisDomicilioId, Integer ciudadDomicilioId, Integer parroquiaDomicilioId,  String barrio,  String referenciaDireccion, String numeroCarnetDiscapacidad) throws PersonaNotFoundException, PersonaExistsException, EmailExistException
    {
        validateDocumentoIdentificionAndEmail(StringUtils.EMPTY, documentoIdentificacion, emailPersonal);

        Persona persona = Persona.builder()
                            .tipopersona(tipoPersona)
                            .documentoidentificacion(documentoIdentificacion)
                            .nombre1(nombre1)
                            .nombre2(nombre2)
                            .apellido1(apellido1)
                            .apellido2(apellido2)
                            .fechanacimiento(fechaNacimiento)
                            .direccion(direccion)
                            .telefonoconvencional(telefonoConvencional)
                            .telefonocelular(telefonoCelular)
                            .operadoraFk(operadoraFk)
                            .emailpersonal(emailPersonal)
                            .emailinstitucional(emailInstitucional)
                            .nacionalidadId(nacionalidadId)
                            .activo(activo)
                            .creadopor(creadoPor)
                            .creado(creado)
                            .fechaingreso(fechaIngreso)
                            .estadoId(estadoId)
                            .tipodocumentoId(tipoDocumentoId)
                            .sexoId(sexoId)
                            .identidadgeneroId(identidadGeneroId)
                            .estadocivilId(estadoCivilId)
                            .tiposangre(tipoSangre)
                            .tipodiscapacidadId(tipoDiscapacidadId)
                            .porcentajediscapacidad(porcentajeDiscapacidad)
                            .descripciondiscapacidad(descripcionDiscapacidad)
                            .idiomaId(idiomaId)
                            .etniaId(etniaId)
                            .paisnacimientoId(paisnacimientoId)
                            .provincianacimientoId(provinciaNacimientoId)
                            .ciudadnacimientoId(ciudadNacimientoId)
                            .parroquianacimientoId(parroquiaNacimientoId)
                            .personacontacto(personaContacto)
                            .telefonoconctacto(telefonoContacto)
                            .paisdomicilioId(paisDomicilioId)
                            .provinciadomicilioId(provinciaDomicilioId)
                            .ciudaddomicilioId(ciudadDomicilioId)
                            .parroquiadomicilioId(parroquiaDomicilioId)
                            .barrio(barrio)
                            .referenciadireccion(referenciaDireccion)
                            .numerocarnetdiscapacidad(numeroCarnetDiscapacidad)
                            .build();


        Persona personaGuardada = personaRepository.save(persona);
        LOGGER.info("La nueva Persona tiene el siguiente id : "+personaGuardada.getPersonaId());

        return persona;
    }

    /**
     * Este metodo me trae null si es un usuario nuevo
     * O me trae el usuario con el documento de Identificacion con el mail personal que se pasa
     * Sino envia una exception dependiendo del caso
     * @param currentDocumentoIdentificacion
     * @param newDocumentoIdentificacion
     * @param newEmailPersonal
     * @return
     * @throws PersonaNotFoundException
     * @throws PersonaExistsException
     * @throws EmailExistException
     */
    private Persona validateDocumentoIdentificionAndEmail(String currentDocumentoIdentificacion, String newDocumentoIdentificacion, String newEmailPersonal)
            throws PersonaNotFoundException, PersonaExistsException, EmailExistException {

            Optional<Persona> personaByNewDocumentoidentificacion = findPersonaByDocumentoidentificacion(newDocumentoIdentificacion);
            Optional<Persona> personaByNewEmailpersonal = findPersonaByEmailpersonal(newEmailPersonal);


            if(StringUtils.isNotBlank(currentDocumentoIdentificacion)) {
                Optional<Persona> currentPersona = findPersonaByDocumentoidentificacion(currentDocumentoIdentificacion);


                if(!currentPersona.isPresent()){
                    LOGGER.error("Persona no encontrada por la identificacion: " + currentDocumentoIdentificacion);
                    throw new PersonaNotFoundException("No existe persona con esta identificacion " +currentDocumentoIdentificacion);
                }


                if(personaByNewDocumentoidentificacion.isPresent() && !currentPersona.get().getPersonaId().equals(personaByNewDocumentoidentificacion.get().getPersonaId()))
                {
                    throw new PersonaExistsException("Ya existe una persona con esta identificacion" + newDocumentoIdentificacion);
                }


                if(personaByNewEmailpersonal.isPresent() && ! currentPersona.get().getPersonaId().equals(personaByNewEmailpersonal.get().getPersonaId()))
                {
                    throw new EmailExistException("Ya existe la persona con este correo electronico: " + newEmailPersonal);
                }
                return currentPersona.get();
            } else {

                if(personaByNewDocumentoidentificacion.isPresent()) {
                    throw new PersonaExistsException("Ya existe la persona con identificacion: " + newDocumentoIdentificacion);
                }

                if(personaByNewEmailpersonal.isPresent())
                {
                    throw new EmailExistException("Ya existe la persona con este correo electronico: " + newEmailPersonal);
                }
                return null;

            }
    }



    @Override
    public List<Persona> getPersonas() {
        return this.personaRepository.findAll();
    }

    @Override
    public Optional<Persona> findPersonaByEmailpersonal(String emailpersonal) {
        return this.personaRepository.findPersonaByEmailpersonal(emailpersonal);
    }

    @Override
    public Optional<Persona> findPersonaByDocumentoidentificacion(String documentoidentificacion) {
      return  this.personaRepository.findPersonaByDocumentoidentificacion(documentoidentificacion);
    }


}