package com.sparkplug.auth.infrastructure.security.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.sparkplug.commonauthentication.contract.PublicKeyProvider;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtIssuerService implements PublicKeyProvider {

    @Value("${jwt.token.expiry.hours}")
    private Long expiryHours;

    private final PrivateKey privateKey;

    private final PublicKey publicKey;

    public JwtIssuerService() throws JOSEException {
        RSAKey jwk = new RSAKeyGenerator(2048)
                .keyUse(KeyUse.SIGNATURE)
                .keyID(UUID.randomUUID().toString())
                .issueTime(new Date())
                .generate();

        privateKey = jwk.toPrivateKey();
        publicKey = jwk.toPublicKey();
    }

    public String issueToken(String username, Map<String, Object> extraClaims) {
        var oneHourInMilliseconds = 1000 * 60 * 60;
        var now = new Date(System.currentTimeMillis());
        var expiryDate = new Date(System.currentTimeMillis() + oneHourInMilliseconds * expiryHours);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(privateKey)
                .compact();
    }

    @Override
    public PublicKey getPublicKey() {
        return publicKey;
    }
}
