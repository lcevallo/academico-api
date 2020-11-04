package com.attinae.academicoapi.repository;

import com.attinae.academicoapi.domain.seguridad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByNombre(String nombre);

}
