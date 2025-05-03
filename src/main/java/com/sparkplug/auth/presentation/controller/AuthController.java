package com.sparkplug.auth.presentation.controller;

import com.sparkplug.auth.application.dto.request.*;
import com.sparkplug.auth.application.dto.response.AuthResponse;
import com.sparkplug.auth.application.dto.response.MeResponse;
import com.sparkplug.auth.application.usecase.AdminRegisterUseCase;
import com.sparkplug.auth.application.usecase.ClientRegisterUseCase;
import com.sparkplug.auth.application.usecase.LoginUseCase;
import com.sparkplug.auth.application.usecase.UpdateCredentialsUseCase;
import com.sparkplug.commonauthentication.user.SparkplugUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final ClientRegisterUseCase clientRegisterUseCase;
    private final AdminRegisterUseCase adminRegisterUseCase;
    private final UpdateCredentialsUseCase updateCredentialsUseCase;

    @Autowired
    public AuthController(
            LoginUseCase loginUseCase,
            ClientRegisterUseCase clientRegisterUseCase,
            AdminRegisterUseCase adminRegisterUseCase,
            UpdateCredentialsUseCase updateCredentialsUseCase) {
        this.loginUseCase = loginUseCase;
        this.clientRegisterUseCase = clientRegisterUseCase;
        this.adminRegisterUseCase = adminRegisterUseCase;
        this.updateCredentialsUseCase = updateCredentialsUseCase;
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> getCurrentUserInfo(@AuthenticationPrincipal SparkplugUserDetails userDetails) {
        return ResponseEntity.ok(
                new MeResponse(
                        userDetails.getId(),
                        userDetails.getEmail(),
                        userDetails.getPhoneNumber(),
                        userDetails.getAuthorities().stream()
                                .map(Object::toString)
                                .toList(),
                        userDetails.getUsername()
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                loginUseCase.login(request));
    }

    @PostMapping("/register/email")
    public ResponseEntity<AuthResponse> registerClientEmail(@RequestBody ClientRegisterEmailRequest request) {
        return ResponseEntity.ok(
                clientRegisterUseCase.registerWithEmail(request));
    }

    @PostMapping("/register/phone-number")
    public ResponseEntity<AuthResponse> registerClientPhoneNumber(@RequestBody ClientRegisterPhoneNumberRequest request) {
        return ResponseEntity.ok(
                clientRegisterUseCase.registerWithPhoneNumber(request));
    }

    @PostMapping("/admins/register/email")
    @PreAuthorize("hasAnyAuthority('ADMIN_MANAGER', 'ADMIN_GOD')")
    public ResponseEntity<AuthResponse> registerAdminEmail(@RequestBody AdminRegisterEmailRequest request) {
        return ResponseEntity.ok(
                adminRegisterUseCase.registerWithEmail(request));
    }

    @PostMapping("/admins/register/phone-number")
    @PreAuthorize("hasAnyAuthority('ADMIN_MANAGER', 'ADMIN_GOD')")
    public ResponseEntity<AuthResponse> registerAdminPhoneNumber(@RequestBody AdminRegisterPhoneNumberRequest request) {
        return ResponseEntity.ok(
                adminRegisterUseCase.registerWithPhoneNumber(request));
    }

    @PutMapping("/email")
    public ResponseEntity<Void> updateEmail(
            @AuthenticationPrincipal SparkplugUserDetails userDetails,
            @RequestBody UpdateEmailRequest emailRequest) {
        updateCredentialsUseCase.updateEmail(userDetails.getId(), emailRequest);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/phone-number")
    public ResponseEntity<Void> updatePhoneNumber(
            @AuthenticationPrincipal SparkplugUserDetails userDetails,
            @RequestBody UpdatePhoneNumberRequest phoneNumberRequest) {
        updateCredentialsUseCase.updatePhoneNumber(userDetails.getId(), phoneNumberRequest);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/username")
    public ResponseEntity<Void> updateUsername(
            @AuthenticationPrincipal SparkplugUserDetails userDetails,
            @RequestBody UpdateUsernameRequest usernameRequest) {
        updateCredentialsUseCase.updateUsername(userDetails.getId(), usernameRequest);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @AuthenticationPrincipal SparkplugUserDetails userDetails,
            @RequestBody UpdatePasswordRequest passwordRequest) {
        updateCredentialsUseCase.updatePassword(userDetails.getId(), passwordRequest);

        return ResponseEntity.ok().build();
    }
}
