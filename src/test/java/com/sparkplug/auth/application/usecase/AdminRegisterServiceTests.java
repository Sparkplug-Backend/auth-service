package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.request.AdminRegisterEmailRequest;
import com.sparkplug.auth.application.dto.request.AdminRegisterPhoneNumberRequest;
import com.sparkplug.auth.application.exception.AlreadyTakenException;
import com.sparkplug.auth.application.service.AdminRegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdminRegisterServiceTests extends ServiceTest {

    @Autowired
    AdminRegisterService service;

    @Test
    void contextLoads() {
        assertNotNull(service);
    }

    @Test
    void registerWithEmail_whenUnique_thenReturnResponse() {
        var request = new AdminRegisterEmailRequest(
                username.value(), email.value(), password.value(), List.of("ADMIN_MANAGER"));

        var response = service.registerWithEmail(request);

        assertNotNull(response);
    }

    @Test
    void registerWithPhoneNumber_whenUnique_thenReturnResponse() {
        var request = new AdminRegisterPhoneNumberRequest(
                username.value(), phoneNumber.value(), password.value(), List.of("ADMIN_MANAGER"));

        var response = service.registerWithPhoneNumber(request);

        assertNotNull(response);
    }

    @Nested
    public class WhenNotUniqueTests {

        @Test
        void registerWithEmail_whenUsernameNotUnique_thenThrow() {
            var request = new AdminRegisterEmailRequest(
                    username.value(), "email@email.com", "password123", List.of("ADMIN_MANAGER"));

            assertThrows(AlreadyTakenException.class, () -> service.registerWithEmail(request));
        }

        @Test
        void registerWithEmail_whenEmailNotUnique_thenThrow() {
            var request = new AdminRegisterEmailRequest(
                    "unique_user", email.value(), "password123", List.of("ADMIN_MANAGER"));

            assertThrows(AlreadyTakenException.class, () -> service.registerWithEmail(request));
        }

        @Test
        void registerWithPhoneNumber_whenPhoneNumberNotUnique_thenThrow() {
            var request = new AdminRegisterPhoneNumberRequest(
                    "unique_user", phoneNumber.value(), "password123", List.of("ADMIN_MANAGER"));

            assertThrows(AlreadyTakenException.class, () -> service.registerWithPhoneNumber(request));
        }

        @BeforeEach
        void insertAdminBeforeEach() {
            insertAdmin();
        }
    }

}
