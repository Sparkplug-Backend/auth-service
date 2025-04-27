package com.sparkplug.auth.application.service;

import com.sparkplug.auth.application.dto.request.LoginRequest;
import com.sparkplug.auth.application.dto.response.AuthResponse;
import com.sparkplug.auth.application.security.JwtAuthenticationService;
import com.sparkplug.auth.application.usecase.LoginUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {

    private final JwtAuthenticationService authenticationService;

    @Autowired
    public LoginService(JwtAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        var token = authenticationService.authenticate(
                request.username(), request.password()
        );

        return new AuthResponse(token);
    }
}
