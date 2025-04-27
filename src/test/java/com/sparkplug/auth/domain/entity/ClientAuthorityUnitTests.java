package com.sparkplug.auth.domain.entity;

import com.sparkplug.auth.domain.enums.ClientRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientAuthorityUnitTests {

    @Test
    void shouldCreateClientAuthorityFromRole() {
        var clientRole = ClientRole.CLIENT_BASIC;
        var authority = ClientAuthority.create(clientRole);

        assertNotNull(authority);
        assertEquals(clientRole.toString(), authority.toString());
    }
}
