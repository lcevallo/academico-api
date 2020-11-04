package com.attinae.academicoapi.repository;

import com.attinae.academicoapi.domain.seguridad.Rolusuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolUsuarioRepository extends JpaRepository<Rolusuario, Integer> {
        List<Rolusuario> findAllByUsuarioId(Integer UsuarioId);
}
