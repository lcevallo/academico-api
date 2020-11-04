package com.attinae.academicoapi.repository;

import com.attinae.academicoapi.domain.seguridad.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    Optional<Usuario> findUsuarioByLogin(String login);

    Usuario findUsuarioByUsuarioId(Integer usuarioId);


    Optional<Usuario> findByPersonaId(Integer personaId);

}