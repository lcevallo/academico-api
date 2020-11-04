package com.attinae.academicoapi.repository;

import com.attinae.academicoapi.domain.seguridad.RolAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RolAuthoritiesRepository extends JpaRepository<RolAuthorities, Integer> {
  List<RolAuthorities> findAllByFkRolId(Integer fkRolId);
}
