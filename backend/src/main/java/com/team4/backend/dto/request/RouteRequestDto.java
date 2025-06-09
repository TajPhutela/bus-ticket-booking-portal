package com.team4.backend.dto.request;

import com.team4.backend.entities.Route;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Route}
 */
public record RouteRequestDto(Integer id, @NotNull @Size(max = 255) String fromCity, @NotNull @Size(max = 255) String toCity,
                              Integer breakPoints, Integer duration) implements Serializable {
}