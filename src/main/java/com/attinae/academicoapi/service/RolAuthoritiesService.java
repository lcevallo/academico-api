package com.attinae.academicoapi.service;

import com.attinae.academicoapi.domain.seguridad.RolAuthorities;
import java.util.List;



public interface RolAuthoritiesService {

    List<RolAuthorities> findAllByFkRolId(Integer fkRolId);
}
