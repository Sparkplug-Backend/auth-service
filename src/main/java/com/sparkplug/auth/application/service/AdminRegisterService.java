package com.sparkplug.auth.application.service;

import com.sparkplug.auth.application.dto.request.AdminRegisterEmailRequest;
import com.sparkplug.auth.application.dto.request.AdminRegisterPhoneNumberRequest;
import com.sparkplug.auth.application.dto.response.AuthResponse;
import com.sparkplug.auth.application.repository.AdminAuthoritiesRepository;
import com.sparkplug.auth.application.repository.AdminsRepository;
import com.sparkplug.auth.application.repository.UsersRepository;
import com.sparkplug.auth.application.security.JwtAuthenticationService;
import com.sparkplug.auth.application.usecase.AdminRegisterUseCase;
import com.sparkplug.auth.domain.contract.PasswordHasher;
import com.sparkplug.auth.domain.entity.Admin;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminRegisterService implements AdminRegisterUseCase {

    private final AdminsRepository adminsRepository;
    private final AdminAuthoritiesRepository authoritiesRepository;
    private final UsersRepository usersRepository;
    private final JwtAuthenticationService authenticationService;
    private final PasswordHasher hasher;
    private final UniquenessValidator uniquenessValidator = new UniquenessValidator();

    public AdminRegisterService(
            AdminsRepository adminsRepository,
            AdminAuthoritiesRepository authoritiesRepository,
            UsersRepository usersRepository,
            JwtAuthenticationService authenticationService,
            PasswordHasher hasher) {
        this.adminsRepository = adminsRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.usersRepository = usersRepository;
        this.authenticationService = authenticationService;
        this.hasher = hasher;
    }

    @Override
    @Transactional
    public AuthResponse registerWithEmail(AdminRegisterEmailRequest request) {
        var admin = createAdmin(request.username(), request.email(), null, request.password(), request.roles());
        adminsRepository.save(admin);

        var token = authenticationService.authenticate(request.username(), request.password());
        return new AuthResponse(token);
    }

    @Override
    @Transactional
    public AuthResponse registerWithPhoneNumber(AdminRegisterPhoneNumberRequest request) {

        var admin = createAdmin(request.username(), null, request.phoneNumber(), request.password(), request.roles());
        adminsRepository.save(admin);

        var token = authenticationService.authenticate(request.username(), request.password());
        return new AuthResponse(token);
    }

    private Admin createAdmin(String usernameValue, String emailValue, String phoneNumberValue, String passwordValue, List<String> roles) {
        var username = new Username(usernameValue);
        var password = new RawPassword(passwordValue);
        var authorities = authoritiesRepository.findAllByNameIn(roles);

        uniquenessValidator.validateUniqueness("Username", username, usersRepository::existsByUsername);

        if (emailValue != null) {
            var email = new Email(emailValue);
            uniquenessValidator.validateUniqueness("Email", email, usersRepository::existsByEmail);

            return Admin.createWithEmail(username, email, password, authorities, hasher);
        }
        var phoneNumber = new PhoneNumber(phoneNumberValue);

        uniquenessValidator.validateUniqueness("Phone number", phoneNumber, usersRepository::existsByPhoneNumber);
        return Admin.createWithPhoneNumber(username, phoneNumber, password, authorities, hasher);
    }
}
