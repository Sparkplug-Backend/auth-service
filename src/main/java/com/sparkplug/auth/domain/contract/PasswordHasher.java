package com.sparkplug.auth.domain.contract;

import com.sparkplug.auth.domain.vo.RawPassword;

public interface PasswordHasher {
    String hashPassword(RawPassword rawPassword);
}
