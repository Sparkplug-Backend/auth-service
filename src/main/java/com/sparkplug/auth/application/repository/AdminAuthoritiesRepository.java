package com.sparkplug.auth.application.repository;

import com.sparkplug.auth.domain.entity.AdminAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminAuthoritiesRepository extends JpaRepository<AdminAuthority, Long> {

    Optional<AdminAuthority> findByName(String name);

    List<AdminAuthority> findAllByNameIn(List<String> names);
}
