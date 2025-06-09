package com.team4.backend.dto.request;

import com.team4.backend.entities.Booking;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link Booking}
 */
public record BookingRequestDto(Integer id, Integer tripId, @NotNull Integer seatNumber, String status) implements Serializable {
}