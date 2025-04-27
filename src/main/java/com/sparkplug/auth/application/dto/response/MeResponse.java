package com.sparkplug.auth.application.dto.response;

import java.util.List;

public record MeResponse(
        Long id,
        String email,
        String phoneNumber,
        List<String> authorities,
        String username
) {
}
