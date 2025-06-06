package com.team4.backend.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Agency}
 */
@Value
public class AgencyDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 255)
    String name;
    @NotNull
    @Size(max = 30)
    String contactPersonName;
    @NotNull
    @Size(max = 255)
    String email;
    @NotNull
    @Size(max = 15)
    String phone;
}