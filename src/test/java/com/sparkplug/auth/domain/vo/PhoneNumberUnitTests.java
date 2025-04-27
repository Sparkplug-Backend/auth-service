package com.sparkplug.auth.domain.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneNumberUnitTests {

    @Test
    void shouldCreateValidPhoneNumber() {
        PhoneNumber phone = new PhoneNumber("+12345678901");
        assertEquals("+12345678901", phone.value());
    }

    @Test
    void shouldThrowExceptionForInvalidPhoneNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> new PhoneNumber("123-abc-789")
        );
        assertTrue(exception.getMessage().contains("Invalid phone number format"));
    }

    @Test
    void shouldEqualPhoneNumbersWithSameValue() {
        PhoneNumber phone1 = new PhoneNumber("+12345678901");
        PhoneNumber phone2 = new PhoneNumber("+12345678901");
        assertEquals(phone1, phone2);
    }
}
