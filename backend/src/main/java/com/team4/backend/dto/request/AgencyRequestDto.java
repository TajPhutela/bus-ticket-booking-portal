package com.team4.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;


public record AgencyRequestDto(Integer id, @NotNull @Size(max = 255) String name,
                               @NotNull @Size(max = 30) String contactPersonName, @NotNull @Size(max = 255) String email,
                               @NotNull @Size(max = 15) String phone) implements Serializable {
}