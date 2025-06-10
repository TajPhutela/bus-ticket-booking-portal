package com.team4.backend;


import com.team4.backend.dto.request.ReviewRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewRequestDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private ReviewRequestDto createValidDto() {
        return new ReviewRequestDto(
                null,
                1,
                2,
                5,
                "Nice trip",
                Instant.now()
        );
    }

    @Test
    void whenAllRequiredFieldsProvided_thenNoViolations() {
        ReviewRequestDto dto = createValidDto();
        Set<ConstraintViolation<ReviewRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenCustomerIdIsNull_thenViolation() {
        ReviewRequestDto dto = new ReviewRequestDto(
                null,
                null,
                2,
                5,
                "Ok",
                Instant.now()
        );
        Set<ConstraintViolation<ReviewRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .hasSize(1)
                .anyMatch(v -> v.getPropertyPath().toString().equals("customerId"));
    }

    @Test
    void whenTripIdIsNull_thenViolation() {
        ReviewRequestDto dto = new ReviewRequestDto(
                null,
                1,
                null,
                5,
                "Ok",
                Instant.now()
        );
        Set<ConstraintViolation<ReviewRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .hasSize(1)
                .anyMatch(v -> v.getPropertyPath().toString().equals("tripId"));
    }

    @Test
    void whenRatingIsNull_thenViolation() {
        ReviewRequestDto dto = new ReviewRequestDto(
                null,
                1,
                2,
                null,
                "Ok",
                Instant.now()
        );
        Set<ConstraintViolation<ReviewRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .hasSize(1)
                .anyMatch(v -> v.getPropertyPath().toString().equals("rating"));
    }
}
