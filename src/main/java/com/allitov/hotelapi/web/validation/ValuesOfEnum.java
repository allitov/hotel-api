package com.allitov.hotelapi.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * The annotated element must be any of enum values. Accepts strings.
 * @author allitov
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValuesOfEnumValidator.class)
public @interface ValuesOfEnum {

    Class<? extends Enum<?>> enumClass();

    String message() default "Must be any of enum {enumClass}.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
