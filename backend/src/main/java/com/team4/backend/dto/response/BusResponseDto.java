package com.team4.backend.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record BusResponseDto(
        Integer id,
        @NotNull @Size(max = 20) String registrationNumber,
        @NotNull Integer capacity,
        @NotNull @Size(max = 30) String type,
        AgencyOfficeResponseDto office
) implements Serializable {
}
