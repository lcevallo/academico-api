package com.attinae.academicoapi.domain.seguridad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
@Table(name = "rolusuario", schema = "seguridad")
public class Rolusuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator ="RolUsuarioSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name ="RolUsuarioSeq", sequenceName = "seguridad.pk_rolusuario_id_seq", allocationSize = 1, initialValue = 1)
    @Basic(optional = false)
    @Column(name = "rolusuario_id")

    private Integer rolusuarioId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "rol_id")
    private Integer rolId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "usuario_id")
    private Integer usuarioId;

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
}
