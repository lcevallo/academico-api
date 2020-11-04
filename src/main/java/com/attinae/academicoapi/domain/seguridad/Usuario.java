package com.attinae.academicoapi.domain.seguridad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario",schema = "seguridad")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator ="usuarioSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name ="usuarioSeq", sequenceName = "seguridad.pk_usuario_id_seq", allocationSize = 1, initialValue = 1)
    @Column(nullable = false,updatable = false , name = "usuario_id")
    private Integer usuarioId;


    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "login")
    private String login;

    @Basic(optional = false)
    @NotNull
    @Column(name = "persona_id")
    private Integer personaId;

    @Column(name = "fecha_ultimo_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimoIngreso;

    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_id")
    private Integer estadoId;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "America/Guayaquil")
    private Date actualizado;

    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private Character activo;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_not_locked")
    private boolean isNotLocked;

    @Size(max = 100)
    @Column(name = "password")
    private String password;

    @Size(max = 100)
    @Column(name = "pregunta_1")
    private String pregunta1;

    @Size(max = 100)
    @Column(name = "respuesta_1")
    private String respuesta1;

    @Size(max = 100)
    @Column(name = "pregunta_2")
    private String pregunta2;

    @Size(max = 100)
    @Column(name = "respuesta_2")
    private String respuesta2;

    @Size(max = 100)
    @Column(name = "pregunta_3")
    private String pregunta3;

    @Size(max = 100)
    @Column(name = "respuesta_3")
    private String respuesta3;

    @Column(name="last_login_date_display")
    private Date lastLoginDateDisplay;

    @Column(name="profile_image_url")
    private String profileImageUrl;
}
