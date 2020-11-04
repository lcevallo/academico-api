package com.attinae.academicoapi.service.impl;


import com.attinae.academicoapi.domain.seguridad.Rolusuario;
import com.attinae.academicoapi.repository.RolUsuarioRepository;
import com.attinae.academicoapi.service.RolUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Qualifier("RolUsuarioService")
public class RolUsuarioServiceImpl implements RolUsuarioService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final RolUsuarioRepository rolUsuarioRepository;

    @Autowired
    public RolUsuarioServiceImpl(RolUsuarioRepository rolUsuarioRepository) {
        this.rolUsuarioRepository = rolUsuarioRepository;
    }

    @Override
    public Rolusuario guardar(Rolusuario rolusuario) {

        rolusuario.setCreado(new Date());
        Rolusuario rolusuarioSaved = this.rolUsuarioRepository.save(rolusuario);
        LOGGER.info("Se ha ingresado un nuevo registro en RolUsuario : " +rolusuarioSaved.getRolusuarioId());
        return rolusuarioSaved;
    }

    @Override
    public List<Rolusuario> findAllByUsuarioId(Integer usuarioId) {
        return this.rolUsuarioRepository.findAllByUsuarioId(usuarioId);
    }
}
