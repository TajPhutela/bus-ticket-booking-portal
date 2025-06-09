package com.team4.backend.dto;

import com.team4.backend.entities.Address;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Address}
 */
public record AddressDto(Integer id, @NotNull @Size(max = 255) String address, @NotNull @Size(max = 255) String city,
                         @NotNull @Size(max = 255) String state,
                         @NotNull @Size(max = 10) String zipCode) implements Serializable {
}