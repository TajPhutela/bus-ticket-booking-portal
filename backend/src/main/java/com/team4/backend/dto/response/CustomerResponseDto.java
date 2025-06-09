package com.team4.backend.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record CustomerResponseDto(
        Integer id,
        @NotNull @Size(max = 255) String name,
        @NotNull @Size(max = 255) String email,
        @NotNull @Size(max = 15) String phone,
        AddressResponseDto address
) implements Serializable {
}
