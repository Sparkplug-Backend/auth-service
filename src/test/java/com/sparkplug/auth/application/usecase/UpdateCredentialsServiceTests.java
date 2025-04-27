package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.request.UpdateEmailRequest;
import com.sparkplug.auth.application.dto.request.UpdatePasswordRequest;
import com.sparkplug.auth.application.dto.request.UpdatePhoneNumberRequest;
import com.sparkplug.auth.application.dto.request.UpdateUsernameRequest;
import com.sparkplug.auth.application.service.UpdateCredentialsService;
import com.sparkplug.auth.domain.entity.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdateCredentialsServiceTests extends ServiceTest {

    @Autowired
    UpdateCredentialsService service;

    @Test
    void contextLoads() {
        assertNotNull(service);
    }

    @Test
    void updatePassword_whenSavedAndRetrieved_thenPasswordHashesMatch() {
        var client = em.find(Client.class, clientId);
        var oldPasswordValue = client.getPasswordHash();
        var newPasswordValue = "password123";

        service.updatePassword(client.getId(), new UpdatePasswordRequest(oldPasswordValue, newPasswordValue));

        var updatedClient = em.find(Client.class, clientId);

        assertEquals(newPasswordValue, updatedClient.getPasswordHash());
    }

    @Test
    void updateEmail_whenSavedAndRetrieved_thenEmailsMatch() {
        var client = em.find(Client.class, clientId);
        var newEmailValue = "udpatedEmail@email.com";

        service.updateEmail(client.getId(), new UpdateEmailRequest(newEmailValue));

        var updatedClient = em.find(Client.class, clientId);

        assertEquals(newEmailValue, updatedClient.getEmail().value());
    }

    @Test
    void updateUsername_whenSavedAndRetrieved_thenUsernamesMatch() {
        var client = em.find(Client.class, clientId);
        var newUsernameValue = "updatedUsername";

        service.updateUsername(client.getId(), new UpdateUsernameRequest(newUsernameValue));

        var updatedClient = em.find(Client.class, clientId);

        assertEquals(newUsernameValue, updatedClient.getUsername().value());
    }

    @Test
    void updatePhoneNumber_whenSavedAndRetrieved_thenPhoneNumbersMatch() {
        var client = em.find(Client.class, clientId);
        var newNumberValue = "+99999999999";

        service.updatePhoneNumber(client.getId(), new UpdatePhoneNumberRequest(newNumberValue));

        var updatedClient = em.find(Client.class, clientId);

        assertEquals(newNumberValue, updatedClient.getPhoneNumber().value());
    }

    @BeforeEach
    void insertUserBeforeEach() {
        insertClient();
    }
}
