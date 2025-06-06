package com.team4.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @Column(name = "trip_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @NotNull
    @Column(name = "boarding_address_id", nullable = false)
    private Integer boardingAddressId;

    @NotNull
    @Column(name = "dropping_address_id", nullable = false)
    private Integer droppingAddressId;

    @NotNull
    @Column(name = "departure_time", nullable = false)
    private Instant departureTime;

    @NotNull
    @Column(name = "arrival_time", nullable = false)
    private Instant arrivalTime;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver1_driver_id", nullable = false)
    private Driver driver1Driver;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver2_driver_id", nullable = false)
    private Driver driver2Driver;

    @NotNull
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @NotNull
    @Column(name = "fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal fare;

    @NotNull
    @Column(name = "trip_date", nullable = false)
    private Instant tripDate;

}