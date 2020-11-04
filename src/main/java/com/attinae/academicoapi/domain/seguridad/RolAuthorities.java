package com.attinae.academicoapi.domain.seguridad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rol_authorities", schema = "seguridad")
public class RolAuthorities implements Serializable {


    @Id
    @NotNull
    @Column(name = "id_rol_authorities")
    private Integer idRolAuthorities;

    @NotNull
    @Column(name = "fk_rol_id")
    private Integer fkRolId;

    @NotNull
    @Column(name = "fk_authority")
    private Integer fkAuthority;
}
