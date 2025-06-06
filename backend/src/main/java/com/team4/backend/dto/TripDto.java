package com.team4.backend.dto;

import com.team4.backend.entities.Trip;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link Trip}
 */
public record TripDto(Integer id, @NotNull Integer boardingAddressId, @NotNull Integer droppingAddressId,
                      @NotNull Instant departureTime, @NotNull Instant arrivalTime, @NotNull Integer availableSeats,
                      @NotNull BigDecimal fare, @NotNull Instant tripDate) implements Serializable {
}