package com.attinae.academicoapi.domain.seguridad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "vi_rolusuario",catalog = "attinae_db", schema = "seguridad")
public class ViRolusuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "usuario_id")
    private Integer usuarioId;
    @Column(name = "persona_id")
    private Integer personaId;
    @Size(max = 20)
    @Column(name = "login")
    private String login;
    @Size(max = 50)
    @Column(name = "emailinstitucional")
    private String emailinstitucional;
    @Size(max = 2147483647)
    @Column(name = "nombre_persona")
    private String nombrePersona;
    @Column(name = "rol_id")
    private Integer rolId;
    @Size(max = 200)
    @Column(name = "rolusuario")
    private String rolusuario;
    @Column(name = "entidadeducativa_id")
    private Integer entidadeducativaId;
    @Size(max = 100)
    @Column(name = "entidadeducativa")
    private String entidadeducativa;
}
