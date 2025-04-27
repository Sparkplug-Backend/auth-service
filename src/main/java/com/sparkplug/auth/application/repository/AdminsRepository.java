package com.sparkplug.auth.application.repository;

import com.sparkplug.auth.domain.entity.Admin;
import com.sparkplug.auth.domain.vo.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminsRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(Username username);
}
