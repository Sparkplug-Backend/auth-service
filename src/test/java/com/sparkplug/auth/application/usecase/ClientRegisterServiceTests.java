package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.request.ClientRegisterEmailRequest;
import com.sparkplug.auth.application.dto.request.ClientRegisterPhoneNumberRequest;
import com.sparkplug.auth.application.exception.AlreadyTakenException;
import com.sparkplug.auth.application.service.ClientRegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientRegisterServiceTests extends ServiceTest {

    @Autowired
    ClientRegisterService service;

    @Test
    void contextLoads() {
        assertNotNull(service);
    }

    @Test
    void registerWithEmail_whenUnique_thenReturnResponse() {
        var request = new ClientRegisterEmailRequest(
                username.value(), email.value(), password.value());

        var response = service.registerWithEmail(request);

        assertNotNull(response);
    }

    @Test
    void registerWithPhoneNumber_whenUnique_thenReturnResponse() {
        var request = new ClientRegisterPhoneNumberRequest(
                username.value(), phoneNumber.value(), password.value());

        var response = service.registerWithPhoneNumber(request);

        assertNotNull(response);
    }

    @Nested
    public class WhenNotUniqueTests {

        @Test
        void registerWithEmail_whenUsernameNotUnique_thenThrow() {
            var request = new ClientRegisterEmailRequest(
                    username.value(), "email@email.com", "password123");

            assertThrows(AlreadyTakenException.class, () -> service.registerWithEmail(request));
        }

        @Test
        void registerWithEmail_whenEmailNotUnique_thenThrow() {
            var request = new ClientRegisterEmailRequest(
                    "unique_user", email.value(), "password123");

            assertThrows(AlreadyTakenException.class, () -> service.registerWithEmail(request));
        }

        @Test
        void registerWithPhoneNumber_whenPhoneNumberNotUnique_thenThrow() {
            var request = new ClientRegisterPhoneNumberRequest(
                    "unique_user", phoneNumber.value(), "password123");

            assertThrows(AlreadyTakenException.class, () -> service.registerWithPhoneNumber(request));
        }

        @BeforeEach
        void insertClientBeforeEach() {
            insertClient();
        }
    }
}
