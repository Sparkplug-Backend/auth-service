package com.sparkplug.auth.application.security;

public interface JwtAuthenticationService {

    String authenticate(String username, String password);
}
