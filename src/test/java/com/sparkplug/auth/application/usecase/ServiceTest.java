package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.domain.MockPasswordHasher;
import com.sparkplug.auth.domain.entity.Admin;
import com.sparkplug.auth.domain.entity.AdminAuthority;
import com.sparkplug.auth.domain.entity.Client;
import com.sparkplug.auth.domain.entity.ClientAuthority;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class ServiceTest {

    @Autowired
    TransactionTemplate transactionTemplate;
    @Autowired
    EntityManager em;

    protected final Username username = new Username("clientUser");
    protected final PhoneNumber phoneNumber = new PhoneNumber("+987654321");
    protected final Email email = new Email("client@example.com");
    protected final RawPassword password = new RawPassword("pass12");

    protected Long clientId;
    protected Long adminId;

    protected void insertClient() {
        transactionTemplate.execute(status -> {

            var clientAuthority = em.find(ClientAuthority.class, 1 );

            // List.of() returns an immutable collection which leads to exception when entity is modified in a single transaction
            // That's why ArrayList is provided
            var client = Client.createWithPhoneNumberAndEmail(
                    username, phoneNumber, email, password, new ArrayList<>(List.of(clientAuthority)), new MockPasswordHasher());

            em.persist(client);

            clientId = client.getId();

            return null;
        });
    }

    protected void insertAdmin() {
        transactionTemplate.execute(status -> {

            var adminAuthority = em.find(AdminAuthority.class, 1 );

            var admin = Admin.createWithPhoneNumberAndEmail(
                    username, phoneNumber, email, password, new ArrayList<>(List.of(adminAuthority)), new MockPasswordHasher());

            em.persist(admin);

            adminId = admin.getId();

            return null;
        });
    }
}
