package com.sparkplug.auth.domain.entity;

import com.sparkplug.auth.domain.MockPasswordHasher;
import com.sparkplug.auth.domain.contract.PasswordHasher;
import com.sparkplug.auth.domain.enums.AdminRole;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdminUnitTests {

    private final Username username = new Username("adminUser");
    private final Email email = new Email("admin@example.com");
    private final RawPassword password = new RawPassword("pass12");
    private final List<AdminAuthority> authorities = List.of(AdminAuthority.create(AdminRole.ADMIN_BASIC));
    private final PhoneNumber phoneNumber = new PhoneNumber("+123456789");

    private final PasswordHasher hasher = new MockPasswordHasher();

    @Test
    void shouldCreateAdminWithEmail() {
        var admin = Admin.createWithEmail(username, email, password, authorities, hasher);

        assertNotNull(admin);
    }

    @Test
    void shouldCreateAdminWithPhoneNumber() {
        var admin = Admin.createWithPhoneNumber(username, phoneNumber, password, authorities, hasher);

        assertNotNull(admin);
    }

    @Test
    void shouldCreateAdminWithPhoneNumberAndEmail() {
        var admin = Admin.createWithPhoneNumberAndEmail(username, phoneNumber, email, password, authorities, hasher);

        assertNotNull(admin);
    }

    @Test
    void shouldGetAuthorities() {
        var admin = Admin.createWithPhoneNumberAndEmail(username, phoneNumber, email, password, authorities, hasher);

        assertTrue(admin.getAuthorities().contains("ADMIN_BASIC"));
    }
}
