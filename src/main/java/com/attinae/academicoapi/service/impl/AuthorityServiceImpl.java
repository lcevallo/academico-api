package com.attinae.academicoapi.service.impl;

import com.attinae.academicoapi.domain.seguridad.Authority;
import com.attinae.academicoapi.repository.AuthorityRepository;
import com.attinae.academicoapi.service.AuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Optional<Authority> findById(Integer id) {
        return this.authorityRepository.findById(id);
    }
}
