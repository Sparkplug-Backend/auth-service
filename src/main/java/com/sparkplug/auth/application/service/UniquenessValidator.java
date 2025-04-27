package com.sparkplug.auth.application.service;

import com.sparkplug.auth.application.exception.AlreadyTakenException;

import java.util.function.Predicate;

public class UniquenessValidator {

    public  <T> void validateUniqueness(String fieldName, T value, Predicate<T> existsCheck) {
        if (existsCheck.test(value)) {
            throw new AlreadyTakenException(fieldName, value.toString());
        }
    }
}
