package com.sparkplug.auth.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AdminRegisterPhoneNumberRequest(
        @NotNull String username,
        @NotNull String phoneNumber,
        @NotNull String password,
        @NotNull List<String> roles
) {
}
