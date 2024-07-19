package com.allitov.hotelapi.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * The annotated class must contain the page number and page size or not contain them at all.
 * @author allitov
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPaginationValidator.class)
public @interface ValidPagination {

    String message() default "Page number and page size both must be specified or not.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
