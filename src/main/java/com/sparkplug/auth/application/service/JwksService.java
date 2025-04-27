package com.sparkplug.auth.application.service;

import com.sparkplug.auth.application.security.JwkProvider;
import com.sparkplug.auth.application.usecase.JwksUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class JwksService implements JwksUseCase {

    private final JwkProvider provider;

    @Autowired
    public JwksService(JwkProvider provider) {
        this.provider = provider;
    }

    @Override
    public Map<String, String> getJwk() {
        return provider.getPublicKey();
    }
}
