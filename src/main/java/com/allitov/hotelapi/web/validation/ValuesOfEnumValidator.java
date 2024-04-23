package com.allitov.hotelapi.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Validation class for the ValuesOfEnum annotation.
 * @author allitov
 */
public class ValuesOfEnumValidator implements ConstraintValidator<ValuesOfEnum, String> {

    private Set<String> acceptedValues;

    @Override
    public void initialize(ValuesOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return acceptedValues.contains(value);
    }
}
