package com.attinae.academicoapi.service;

import com.attinae.academicoapi.domain.seguridad.Rolusuario;

import java.util.List;

public interface RolUsuarioService {

    Rolusuario guardar(Rolusuario rolusuario);

    List<Rolusuario> findAllByUsuarioId(Integer usuarioId);
}
