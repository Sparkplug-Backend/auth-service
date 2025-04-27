package com.sparkplug.auth.presentation.controller;

import com.sparkplug.auth.application.usecase.JwksUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JwksController {

    private final JwksUseCase jwksUseCase;

    @Autowired
    public JwksController(JwksUseCase jwksUseCase) {
        this.jwksUseCase = jwksUseCase;
    }

    @GetMapping(".well-known/jwks.json")
    public Map<String, String> getJwks() {
        return jwksUseCase.getJwk();
    }
}
