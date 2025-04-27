package com.sparkplug.auth.domain.entity;

import com.sparkplug.auth.domain.MockPasswordHasher;
import com.sparkplug.auth.domain.contract.PasswordHasher;
import com.sparkplug.auth.domain.enums.ClientRole;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientUnitTests {

    private final Username username = new Username("clientUser");
    private final PhoneNumber phoneNumber = new PhoneNumber("+987654321");
    private final Email email = new Email("client@example.com");
    private final RawPassword password = new RawPassword("pass12");
    private final List<ClientAuthority> authorities = List.of(ClientAuthority.create(ClientRole.CLIENT_BASIC));

    private final PasswordHasher hasher = new MockPasswordHasher();

    @Test
    void shouldCreateClientWithEmail() {
        var client = Client.createWithEmail(username, email, password, authorities, hasher);

        assertNotNull(client);
        assertEquals("CLIENT_BASIC", client.getAuthorities().getFirst());
    }

    @Test
    void shouldCreateClientWithPhoneNumber() {
        var client = Client.createWithPhoneNumber(username, phoneNumber, password, authorities, hasher);

        assertNotNull(client);
    }

    @Test
    void shouldCreateClientWithPhoneNumberAndEmail() {
        var client = Client.createWithPhoneNumberAndEmail(username, phoneNumber, email, password, authorities, hasher);

        assertNotNull(client);
    }

    @Test
    void shouldGetAuthorities() {
        var client = Client.createWithPhoneNumberAndEmail(username, phoneNumber, email, password, authorities, hasher);
        assertTrue(client.getAuthorities().contains("CLIENT_BASIC"));
    }
}
