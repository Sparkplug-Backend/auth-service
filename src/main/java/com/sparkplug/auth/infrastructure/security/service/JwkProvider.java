package com.sparkplug.auth.infrastructure.security.service;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@Service
public class JwkProvider implements com.sparkplug.auth.application.security.JwkProvider {

    private final JwtIssuerService service;

    @Autowired
    public JwkProvider(JwtIssuerService service) {
        this.service = service;
    }

    @Override
    public Map<String, String> getPublicKey() {
        var publicKey = service.getPublicKey();

        if (!(publicKey instanceof RSAPublicKey rsaPublicKey)) {
            throw new IllegalArgumentException("Public key must be an RSA key");
        }

        var jwk = new RSAKey.Builder(rsaPublicKey)
                .algorithm(Algorithm.parse(rsaPublicKey.getAlgorithm()))
                .keyUse(KeyUse.SIGNATURE)
                .build()
                .toPublicJWK();

        return Map.of(
                "kty", jwk.getKeyType().getValue(),
                "use", jwk.getKeyUse().getValue(),
                "alg", jwk.getAlgorithm().getName(),
                "n", jwk.getModulus().toString(),
                "e", jwk.getPublicExponent().toString()
        );
    }
}
