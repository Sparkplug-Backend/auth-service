package com.sparkplug.auth.domain;

import com.sparkplug.auth.domain.contract.PasswordHasher;
import com.sparkplug.auth.domain.vo.RawPassword;

public class MockPasswordHasher implements PasswordHasher {
    @Override
    public String hashPassword(RawPassword rawPassword) {
        return rawPassword.value();
    }
}
