package com.attinae.academicoapi.service.impl;

import com.attinae.academicoapi.domain.seguridad.Rol;
import com.attinae.academicoapi.repository.RolRepository;
import com.attinae.academicoapi.service.RolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Qualifier("rolServiceImpl")
public class RolServiceImpl implements RolService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final RolRepository rolRepository;

    @Autowired
    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<Rol> findAll() {
        return this.rolRepository.findAll();
    }

    @Override
    public Optional<Rol> findByNombre(String nombre) {
        return this.rolRepository.findByNombre(nombre);
    }
}
