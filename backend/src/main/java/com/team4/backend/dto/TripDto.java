package com.team4.backend.dto;

import com.team4.backend.entities.Bus;
import com.team4.backend.entities.Driver;
import com.team4.backend.entities.Route;
import com.team4.backend.entities.Trip;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public record TripDto(Integer id, @NotNull Route route, @NotNull Bus bus, @NotNull Integer boardingAddressId, @NotNull Integer droppingAddressId, @NotNull Instant departureTime, @NotNull Instant arrivalTime, @NotNull Driver driver1Driver, @NotNull Driver driver2Driver, @NotNull Integer availableSeats, @NotNull BigDecimal fare, @NotNull Instant tripDate) implements Serializable {
  }