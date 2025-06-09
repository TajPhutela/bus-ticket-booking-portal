package com.team4.backend.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record DriverResponseDto(
        Integer id, @NotNull @Size(max = 20) String licenseNumber,
        @NotNull @Size(max = 255) String name, @NotNull @Size(max = 15) String phone,
        AgencyOfficeResponseDto office, AddressResponseDto address
) implements Serializable {
}
