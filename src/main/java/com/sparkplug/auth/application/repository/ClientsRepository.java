package com.sparkplug.auth.application.repository;

import com.sparkplug.auth.domain.entity.Client;
import com.sparkplug.auth.domain.vo.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByUsername(Username username);
}
