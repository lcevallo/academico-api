package com.attinae.academicoapi.domain;


import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.TemporalType.TIMESTAMP;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name ="persona", schema = "academico")
public class Persona  {

    @Id
    @GeneratedValue(generator ="personaSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name ="personaSeq", sequenceName = "academico.pk_persona_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "persona_id")
    private Integer personaId;

    @Column(name = "tipopersona")
    private Character tipopersona;


    @NotBlank
    @Size(min = 1, max = 15)
    @Column(name = "documentoidentificacion")
    private String documentoidentificacion;


    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "nombre1")
    private String nombre1;

    @Size(max = 50)
    @Column(name = "nombre2")
    private String nombre2;

    @Basic(optional = false)
    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "apellido1")
    private String apellido1;
    @Size(max = 50)
    @Column(name = "apellido2")
    private String apellido2;
    @Column(name = "fechanacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechanacimiento;
    @Size(max = 200)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 10)
    @Column(name = "telefonoconvencional")
    private String telefonoconvencional;
    @Size(max = 10)
    @Column(name = "telefonocelular")
    private String telefonocelular;

    @Size(max = 50)
    @Column(name = "emailpersonal")
    private String emailpersonal;

    @Size(max = 50)
    @Column(name = "emailinstitucional")
    private String emailinstitucional;


    @NotNull
    @Column(name = "nacionalidad_id")
    private Integer nacionalidadId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private Character activo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "creadopor")
    private Integer creadopor;

    @Basic(optional = false)
    @Column(name = "creado")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "America/Guayaquil")
    private Date creado;

    @Column(name = "actualizadopor")
    private Integer actualizadopor;

    @Column(name = "actualizado")
    @Temporal(TIMESTAMP)
    private Date actualizado;

    @Column(name = "fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;

    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_id")
    private Integer estadoId;

    @Column(name = "tipodocumento_id")
    private Integer tipodocumentoId;
    @Column(name = "sexo_id")
    private Integer sexoId;
    @Column(name = "identidadgenero_id")
    private Integer identidadgeneroId;
    @Column(name = "estadocivil_id")
    private Integer estadocivilId;
    @Column(name = "tiposangre")
    private String tiposangre;
    @Column(name = "tipodiscapacidad_id")
    private Integer tipodiscapacidadId;
    @Column(name = "porcentajediscapacidad")
    private Short porcentajediscapacidad;
    @Size(max = 2147483647)
    @Column(name = "descripciondiscapacidad")
    private String descripciondiscapacidad;
    @Column(name = "idioma_id")
    private Integer idiomaId;
    @Column(name = "etnia_id")
    private Integer etniaId;
    @Column(name = "paisnacimiento_id")
    private Integer paisnacimientoId;
    @Column(name = "provincianacimiento_id")
    private Integer provincianacimientoId;
    @Column(name = "ciudadnacimiento_id")
    private Integer ciudadnacimientoId;
    @Column(name = "parroquianacimiento_id")
    private Integer parroquianacimientoId;
    @Size(max = 200)
    @Column(name = "personacontacto")
    private String personacontacto;
    @Size(max = 10)
    @Column(name = "telefonoconctacto")
    private String telefonoconctacto;
    @Column(name = "paisdomicilio_id")
    private Integer paisdomicilioId;
    @Column(name = "provinciadomicilio_id")
    private Integer provinciadomicilioId;
    @Column(name = "ciudaddomicilio_id")
    private Integer ciudaddomicilioId;
    @Column(name = "parroquiadomicilio_id")
    private Integer parroquiadomicilioId;
    @Size(max = 100)
    @Column(name = "barrio")
    private String barrio;
    @Size(max = 2147483647)
    @Column(name = "referenciadireccion")
    private String referenciadireccion;
    @Column(name = "operadora_fk")
    private Integer operadoraFk;
    @Column(name = "numerocarnetdiscapacidad")
    private String numerocarnetdiscapacidad;

}