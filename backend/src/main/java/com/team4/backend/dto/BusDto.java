package com.team4.backend.dto;

import com.team4.backend.entities.Bus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Bus}
 */
public record BusDto(
        Integer id,
        @NotNull @Size(max = 20) String registrationNumber,
        @NotNull Integer capacity,
        @NotNull @Size(max = 30) String type,
        Integer officeId
) implements Serializable {}
