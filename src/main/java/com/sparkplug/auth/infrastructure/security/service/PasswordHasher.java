package com.sparkplug.auth.infrastructure.security.service;

import com.sparkplug.auth.domain.vo.RawPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher implements com.sparkplug.auth.domain.contract.PasswordHasher {

    private final PasswordEncoder encoder;

    @Autowired
    public PasswordHasher(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String hashPassword(RawPassword rawPassword) {
        return encoder.encode(rawPassword.value());
    }
}
