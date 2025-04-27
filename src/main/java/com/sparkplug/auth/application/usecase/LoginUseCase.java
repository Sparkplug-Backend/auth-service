package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.request.LoginRequest;
import com.sparkplug.auth.application.dto.response.AuthResponse;

public interface LoginUseCase {
    AuthResponse login(LoginRequest loginRequest);
}
