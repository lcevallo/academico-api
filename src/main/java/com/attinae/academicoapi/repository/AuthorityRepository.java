package com.attinae.academicoapi.repository;

import com.attinae.academicoapi.domain.seguridad.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findById(Integer id);
}
