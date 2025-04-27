package com.sparkplug.auth.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record ClientRegisterPhoneNumberRequest(
        @NotNull String username,
        @NotNull String phoneNumber,
        @NotNull String password
) {
}
