package com.team4.backend.dto;

import com.team4.backend.entities.Booking;
import com.team4.backend.entities.Trip;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link Booking}
 */
public record BookingDto(Integer id, Integer tripId, @NotNull Integer seatNumber, String status) implements Serializable {
}