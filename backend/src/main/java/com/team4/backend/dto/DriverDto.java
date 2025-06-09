package com.team4.backend.dto;

import com.team4.backend.entities.Address;
import com.team4.backend.entities.AgencyOffice;
import com.team4.backend.entities.Driver;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Driver}
 */
public record DriverDto(Integer id, @NotNull @Size(max = 20) String licenseNumber,
                        @NotNull @Size(max = 255) String name, @NotNull @Size(max = 15) String phone,
                        Integer officeId, Integer addressId) implements Serializable {
}