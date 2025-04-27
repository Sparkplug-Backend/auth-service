package com.sparkplug.auth.domain.entity;

import com.sparkplug.auth.domain.contract.PasswordHasher;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "client", schema = "auth")
@NoArgsConstructor(access = PROTECTED)
public class Client extends User {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "client_authority_client",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "client_authority_id"))
    private List<ClientAuthority> authorities;

    @Override
    public List<String> getAuthorities() {
        return authorities.stream()
                .map(ClientAuthority::toString)
                .toList();
    }

    @Builder(access = PRIVATE)
    private Client(
            Long id, Username username, Email email, PhoneNumber phoneNumber, String passwordHash,
            List<ClientAuthority> authorities) {
        super(id, username, email, phoneNumber, passwordHash);
        this.authorities = authorities;
    }

    private Client(List<ClientAuthority> authorities) {
        this.authorities = authorities;
    }

    public static Client createWithEmail(
            Username username, Email email, RawPassword password, List<ClientAuthority> authorities, PasswordHasher hasher) {

        var passwordHash = hasher.hashPassword(password);

        return Client.getBaseBuilder(username, passwordHash, authorities)
                .email(email)
                .build();
    }

    public static Client createWithPhoneNumber(
            Username username, PhoneNumber phoneNumber, RawPassword password, List<ClientAuthority> authorities, PasswordHasher hasher) {

        var passwordHash = hasher.hashPassword(password);

        return Client.getBaseBuilder(username, passwordHash, authorities)
                .phoneNumber(phoneNumber)
                .build();
    }

    public static Client createWithPhoneNumberAndEmail(
            Username username, PhoneNumber phoneNumber, Email email, RawPassword password, List<ClientAuthority> authorities, PasswordHasher hasher) {

        var passwordHash = hasher.hashPassword(password);

        return Client.getBaseBuilder(username, passwordHash, authorities)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }

    private static ClientBuilder getBaseBuilder(Username username, String passwordHash, List<ClientAuthority> authorities) {

        return Client.builder()
                .username(username)
                .passwordHash(passwordHash)
                .authorities(authorities);
    }
}
