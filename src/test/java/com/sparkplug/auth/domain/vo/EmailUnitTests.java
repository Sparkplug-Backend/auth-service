package com.sparkplug.auth.domain.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailUnitTests {

    @Test
    void shouldCreateValidEmail() {
        Email email = new Email("test@example.com");
        assertEquals("test@example.com", email.value());
    }

    @Test
    void shouldThrowExceptionForInvalidEmail() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> new Email("invalid-email")
        );
        assertTrue(exception.getMessage().contains("Invalid email format"));
    }

    @Test
    void shouldEqualEmailsWithSameValue() {
        Email email1 = new Email("user@example.com");
        Email email2 = new Email("user@example.com");
        assertEquals(email1, email2);
    }
}
