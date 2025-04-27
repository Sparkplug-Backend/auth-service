package com.sparkplug.auth.domain.entity;

import com.sparkplug.auth.domain.enums.AdminRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminAuthorityUnitTests {

    @Test
    void shouldCreateAdminAuthorityFromRole() {
        var adminRole = AdminRole.ADMIN_BASIC;
        var authority = AdminAuthority.create(adminRole);

        assertNotNull(authority);
        assertEquals(adminRole.toString(), authority.toString());
    }
}
