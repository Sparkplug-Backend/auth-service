package com.sparkplug.auth.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record ClientRegisterEmailRequest(
        @NotNull String username,
        @NotNull String email,
        @NotNull String password
) {
}