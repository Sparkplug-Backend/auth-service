package com.sparkplug.auth.application.repository;

import com.sparkplug.auth.domain.entity.User;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(Username username);
    boolean existsByEmail(Email email);
    boolean existsByPhoneNumber(PhoneNumber phoneNumber);

    Optional<User> findByUsername(Username username);
}
