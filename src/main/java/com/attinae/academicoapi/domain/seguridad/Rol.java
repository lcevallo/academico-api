package com.attinae.academicoapi.domain.seguridad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "rol", schema = "seguridad")
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rol_id")
    private Integer rolId;

    @Size(max = 200)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_id")
    private Integer estadoId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "creadopor")
    private int creadopor;

    @Basic(optional = false)
    @NotNull
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

    @NotNull
    @Column(name = "entidadeducativa_id")
    private Integer entidadeducativaId;
}
