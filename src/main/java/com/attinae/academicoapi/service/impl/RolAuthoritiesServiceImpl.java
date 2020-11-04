package com.attinae.academicoapi.service.impl;

import com.attinae.academicoapi.domain.seguridad.RolAuthorities;
import com.attinae.academicoapi.repository.RolAuthoritiesRepository;
import com.attinae.academicoapi.service.RolAuthoritiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RolAuthoritiesServiceImpl  implements RolAuthoritiesService {



    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final RolAuthoritiesRepository rolAuthoritiesRepository;

    @Autowired
    public RolAuthoritiesServiceImpl(RolAuthoritiesRepository rolAuthoritiesRepository) {
        this.rolAuthoritiesRepository = rolAuthoritiesRepository;
    }

    @Override
    public List<RolAuthorities> findAllByFkRolId(Integer fkRolId) {
        return this.rolAuthoritiesRepository.findAllByFkRolId(fkRolId);
    }
}
