package com.allitov.hotelapi.web.dto.filter;

import com.allitov.hotelapi.exception.ExceptionMessage;
import com.allitov.hotelapi.web.validation.ValidPagination;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * An abstract class for all filters. Implements validation for pagination.
 * @author allitov
 */
@Getter
@Setter
@EqualsAndHashCode
@ValidPagination(message = ExceptionMessage.FILTER_INVALID_PAGINATION)
public abstract class AbstractFilter {

    @Positive(message = ExceptionMessage.FILTER_INVALID_PAGE_SIZE)
    @Schema(example = "1")
    protected Integer pageSize;

    @PositiveOrZero(message = ExceptionMessage.FILTER_INVALID_PAGE_NUMBER)
    @Schema(example = "0")
    protected Integer pageNumber;
}
