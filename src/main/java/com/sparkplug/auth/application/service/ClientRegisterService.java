package com.sparkplug.auth.application.service;

import com.sparkplug.auth.application.dto.request.ClientRegisterPhoneNumberRequest;
import com.sparkplug.auth.application.dto.request.ClientRegisterEmailRequest;
import com.sparkplug.auth.application.dto.response.AuthResponse;
import com.sparkplug.auth.application.security.JwtAuthenticationService;
import com.sparkplug.auth.application.usecase.ClientRegisterUseCase;
import com.sparkplug.auth.domain.contract.PasswordHasher;
import com.sparkplug.auth.domain.entity.Client;
import com.sparkplug.auth.domain.entity.ClientAuthority;
import com.sparkplug.auth.domain.enums.ClientRole;
import com.sparkplug.auth.application.repository.ClientAuthoritiesRepository;
import com.sparkplug.auth.application.repository.ClientsRepository;
import com.sparkplug.auth.application.repository.UsersRepository;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientRegisterService implements ClientRegisterUseCase {

    private final ClientsRepository clientsRepository;
    private final ClientAuthoritiesRepository authoritiesRepository;
    private final UsersRepository usersRepository;
    private final JwtAuthenticationService authenticationService;
    private final PasswordHasher hasher;
    private final UniquenessValidator uniquenessValidator = new UniquenessValidator();

    @Autowired
    public ClientRegisterService(
            ClientsRepository clientsRepository,
            ClientAuthoritiesRepository authoritiesRepository,
            UsersRepository usersRepository,
            JwtAuthenticationService authenticationService,
            PasswordHasher hasher) {
        this.clientsRepository = clientsRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.usersRepository = usersRepository;
        this.authenticationService = authenticationService;
        this.hasher = hasher;
    }

    @Override
    @Transactional
    public AuthResponse registerWithEmail(ClientRegisterEmailRequest request) {

        var client = createClient(request.username(), request.email(), null, request.password());
        clientsRepository.save(client);

        var token = authenticationService.authenticate(request.username(), request.password());
        return new AuthResponse(token);
    }

    @Override
    @Transactional
    public AuthResponse registerWithPhoneNumber(ClientRegisterPhoneNumberRequest request) {

        var client = createClient(request.username(), null, request.phoneNumber(), request.password());
        clientsRepository.save(client);

        var token = authenticationService.authenticate(request.username(), request.password());
        return new AuthResponse(token);
    }

    private Client createClient(String usernameValue, String emailValue, String phoneNumberValue, String passwordValue) {
        var basicRole = authoritiesRepository.findByName(ClientRole.CLIENT_BASIC.toString())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + ClientRole.CLIENT_BASIC));
        List<ClientAuthority> roles = List.of(basicRole);

        var username = new Username(usernameValue);
        var password = new RawPassword(passwordValue);

        uniquenessValidator.validateUniqueness("Username", username, usersRepository::existsByUsername);

        if (emailValue != null) {
            var email = new Email(emailValue);
            uniquenessValidator.validateUniqueness("Email", email, usersRepository::existsByEmail);

            return Client.createWithEmail(username, email, password, roles, hasher);
        }
        var phoneNumber = new PhoneNumber(phoneNumberValue);
        uniquenessValidator.validateUniqueness("Phone number", phoneNumber, usersRepository::existsByPhoneNumber);

        return Client.createWithPhoneNumber(username, phoneNumber, password, roles, hasher);
    }
}
