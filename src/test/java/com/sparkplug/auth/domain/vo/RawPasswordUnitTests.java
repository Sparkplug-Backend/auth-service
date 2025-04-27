package com.sparkplug.auth.domain.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RawPasswordUnitTests {
    @Test
    void shouldCreateValidPassword() {
        RawPassword password = new RawPassword("pass12");
        assertEquals("pass12", password.value());
    }

    @Test
    void shouldThrowExceptionForInvalidPassword() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> new RawPassword("12345")
        );
        assertTrue(exception.getMessage().contains("Password must be at least 6 characters long"));
    }

    @Test
    void shouldEqualPasswordsWithSameValue() {
        RawPassword pass1 = new RawPassword("abcdef");
        RawPassword pass2 = new RawPassword("abcdef");
        assertEquals(pass1, pass2);
    }

}
