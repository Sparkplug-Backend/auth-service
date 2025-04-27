package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.request.LoginRequest;
import com.sparkplug.auth.application.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginServiceTests extends ServiceTest {

    @Autowired
    LoginService loginService;

    @Test
    void contextLoads() {
        assertNotNull(loginService);
    }

    @Test
    void login_whenUserDoesntExist_thenThrow() {
        var loginRequest = new LoginRequest(username.value(), password.value());

        assertThrows(BadCredentialsException.class, () -> loginService.login(loginRequest));
    }

    @Nested
    class whenUserExistsTests {

        @Test
        void login_whenUserExistsAndCredentialsCorrect_thenReturnResponse() {
            var loginRequest = new LoginRequest(username.value(), password.value());

            var response = loginService.login(loginRequest);

            assertNotNull(response);
        }

        @Test
        void login_whenUserExistsAndPasswordIncorrect_thenThrow() {
            var loginRequest = new LoginRequest(username.value(), "abc");

            assertThrows(BadCredentialsException.class, () -> loginService.login(loginRequest));
        }

        @Test
        void login_whenUserExistsAndUsernameIncorrect_thenThrow() {
            var loginRequest = new LoginRequest("abcdef", password.value());

            assertThrows(BadCredentialsException.class, () -> loginService.login(loginRequest));
        }

        @BeforeEach
        void insertClientBeforeEach() {
            insertClient();
        }

    }

}
