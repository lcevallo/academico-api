package com.attinae.academicoapi.service;

import com.attinae.academicoapi.domain.seguridad.Authority;

import java.util.Optional;

public interface AuthorityService {
    Optional<Authority> findById(Integer id);
}
