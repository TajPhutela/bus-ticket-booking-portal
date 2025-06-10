package com.team4.backend.dto.request;

import com.team4.backend.entities.Trip;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link Trip}
 */
public record TripRequestDto(
        Integer id,
        @NotNull Integer routeId,
        @NotNull Integer busId,
        @NotNull Integer driver1Id,
        @NotNull Integer driver2Id,
        @NotNull Integer boardingAddressId,
        @NotNull Integer droppingAddressId,
        @NotNull Instant departureTime,
        @NotNull Instant arrivalTime,
        @NotNull Integer availableSeats,
        @NotNull BigDecimal fare,
        @NotNull Instant tripDate
) implements Serializable {}
