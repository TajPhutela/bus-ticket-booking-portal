package com.team4.backend.dto;

import com.team4.backend.entities.Review;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link Review}
 */
public record ReviewDto(
        Integer id,
        @NotNull Integer customerId,
        @NotNull Integer tripId,
        @NotNull Integer rating,
        String comment,
        Instant reviewDate
) implements Serializable {}
