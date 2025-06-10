package com.team4.backend.dto.response;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public record TripResponseDto(
        Integer id,
        @NotNull RouteResponseDto route,
        @NotNull BusResponseDto bus,
        @NotNull DriverResponseDto driver1Driver,
        @NotNull DriverResponseDto driver2Driver,
        @NotNull Integer boardingAddressId,
        @NotNull Integer droppingAddressId,
        @NotNull Instant departureTime,
        @NotNull Instant arrivalTime,
        @NotNull Integer availableSeats,
        @NotNull BigDecimal fare,
        @NotNull Instant tripDate
) implements Serializable {
}
