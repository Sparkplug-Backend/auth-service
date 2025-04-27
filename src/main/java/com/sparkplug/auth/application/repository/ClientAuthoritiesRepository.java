package com.sparkplug.auth.application.repository;

import com.sparkplug.auth.domain.entity.ClientAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientAuthoritiesRepository extends JpaRepository<ClientAuthority, Long> {

    Optional<ClientAuthority> findByName(String name);

    List<ClientAuthority> findAllByNameIn(List<String> names);
}
