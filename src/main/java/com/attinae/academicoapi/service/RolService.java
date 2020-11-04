package com.attinae.academicoapi.service;

import com.attinae.academicoapi.domain.seguridad.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> findAll();
    Optional<Rol> findByNombre(String nombre);

}
