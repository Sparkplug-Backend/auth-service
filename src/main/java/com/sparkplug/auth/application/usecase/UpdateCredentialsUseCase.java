package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.request.UpdateEmailRequest;
import com.sparkplug.auth.application.dto.request.UpdatePasswordRequest;
import com.sparkplug.auth.application.dto.request.UpdatePhoneNumberRequest;
import com.sparkplug.auth.application.dto.request.UpdateUsernameRequest;

public interface UpdateCredentialsUseCase {

    void updatePassword(Long userId, UpdatePasswordRequest request);

    void updateEmail(Long userId, UpdateEmailRequest request);

    void updatePhoneNumber(Long userId, UpdatePhoneNumberRequest request);

    void updateUsername(Long userId, UpdateUsernameRequest request);
}
