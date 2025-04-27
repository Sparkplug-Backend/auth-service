package com.sparkplug.auth.domain.entity;

import com.sparkplug.auth.domain.contract.PasswordHasher;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.*;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Table(name = "base_user", schema = "auth")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "username", nullable = false, unique = true))
    protected Username username;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true))
    protected Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "phone_number", nullable = false, unique = true))
    protected PhoneNumber phoneNumber;

    protected String passwordHash;

    public abstract List<String> getAuthorities();

    public void changeUsername(Username username) {
        this.username = username;
    }

    public void changeEmail(Email email) {
        this.email = email;
    }

    public void changePhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void changePassword(RawPassword oldPassword, RawPassword newPassword, PasswordHasher hasher) {
        var oldPasswordHash = hasher.hashPassword(oldPassword);
        var newPasswordHash = hasher.hashPassword(newPassword);
        if(!this.passwordHash.equals(oldPasswordHash)) {
            throw new IllegalArgumentException("Passwords dont match.");
        }

        this.passwordHash = newPasswordHash;
    }
}
