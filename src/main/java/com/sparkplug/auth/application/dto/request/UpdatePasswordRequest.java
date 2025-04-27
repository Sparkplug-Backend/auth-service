package com.sparkplug.auth.application.dto.request;

public record UpdatePasswordRequest(
        String oldPassword,
        String newPassword) {
}
