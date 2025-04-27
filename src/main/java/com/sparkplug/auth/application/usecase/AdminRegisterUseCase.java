package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.request.AdminRegisterEmailRequest;
import com.sparkplug.auth.application.dto.request.AdminRegisterPhoneNumberRequest;
import com.sparkplug.auth.application.dto.response.AuthResponse;

public interface AdminRegisterUseCase {

    AuthResponse registerWithPhoneNumber(AdminRegisterPhoneNumberRequest request);
    AuthResponse registerWithEmail(AdminRegisterEmailRequest request);
}
