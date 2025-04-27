package com.sparkplug.auth.infrastructure.security.service;

import com.sparkplug.commonauthentication.user.SparkplugUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class JwtAuthenticationService
        implements com.sparkplug.auth.application.security.JwtAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtIssuerService jwtIssuerService;

    @Autowired
    public JwtAuthenticationService(AuthenticationManager authenticationManager, JwtIssuerService jwtIssuerService) {
        this.authenticationManager = authenticationManager;
        this.jwtIssuerService = jwtIssuerService;
    }

    @Override
    public String authenticate(String username, String password) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        var userDetails = (SparkplugUserDetails) authentication.getPrincipal();

        var extraClaims = new HashMap<String, Object>();

        extraClaims.put("id", userDetails.getId());
        extraClaims.put("username", userDetails.getUsername());
        extraClaims.put("email", userDetails.getEmail());
        extraClaims.put("phoneNumber", userDetails.getPhoneNumber());
        extraClaims.put("authorities", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::toString)
                .toList()
        );

        return jwtIssuerService.issueToken(username, extraClaims);
    }
}
