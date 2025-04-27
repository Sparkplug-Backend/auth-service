package com.sparkplug.auth.domain.vo;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public record Email(String value) {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public Email(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }
        this.value = value;
    }

    private boolean isValid(String value) {
        return value != null && EMAIL_PATTERN.matcher(value).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || this.getClass() != o.getClass())
            return false;

        Email other = (Email) o;

        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
