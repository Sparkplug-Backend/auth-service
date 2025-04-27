package com.sparkplug.auth.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AdminRegisterEmailRequest(
        @NotNull String username,
        @NotNull String email,
        @NotNull String password,
        @NotNull List<String> roles
) {}


