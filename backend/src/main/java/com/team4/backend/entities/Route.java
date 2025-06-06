package com.team4.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "routes")
public class Route {
    @Id
    @Column(name = "route_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "from_city", nullable = false)
    private String fromCity;

    @Size(max = 255)
    @NotNull
    @Column(name = "to_city", nullable = false)
    private String toCity;

    @Column(name = "break_points")
    private Integer breakPoints;

    @Column(name = "duration")
    private Integer duration;

}