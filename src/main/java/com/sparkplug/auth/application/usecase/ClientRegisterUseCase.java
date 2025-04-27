package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.request.ClientRegisterPhoneNumberRequest;
import com.sparkplug.auth.application.dto.response.AuthResponse;
import com.sparkplug.auth.application.dto.request.ClientRegisterEmailRequest;

public interface ClientRegisterUseCase {

    AuthResponse registerWithEmail(ClientRegisterEmailRequest request);
    AuthResponse registerWithPhoneNumber(ClientRegisterPhoneNumberRequest request);
}
