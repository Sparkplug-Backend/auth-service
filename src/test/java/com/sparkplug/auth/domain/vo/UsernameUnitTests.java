package com.sparkplug.auth.domain.vo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsernameUnitTests {

    @Test
    void shouldCreateValidUsername() {
        Username username = new Username("valid_user");
        Assertions.assertEquals(username.value(), "valid_user");
    }

    @Test
    void shouldThrowExceptionForInvalidUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Username("in@valid"));
    }

    @Test
    void shouldEqualUsernamesWithSameValue() {
        Username user1 = new Username("validuser");
        Username user2 = new Username("validuser");
        assertEquals(user1, user2);
    }
}
