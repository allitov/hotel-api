package com.allitov.hotelapi.web.validation;

import com.allitov.hotelapi.web.dto.filter.AbstractFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validation class for the ValidPagination annotation.
 * @author allitov
 */
public class ValidPaginationValidator implements ConstraintValidator<ValidPagination, AbstractFilter> {

    @Override
    public boolean isValid(AbstractFilter filter, ConstraintValidatorContext context) {
        if (filter == null) {
            return true;
        }
        boolean bothNull = filter.getPageNumber() == null && filter.getPageSize() == null;
        boolean bothNotNull = filter.getPageNumber() != null && filter.getPageSize() != null;

        return bothNull || bothNotNull;
    }
}
