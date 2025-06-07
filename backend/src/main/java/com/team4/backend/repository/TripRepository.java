package com.team4.backend.repository;

import com.team4.backend.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    Optional<Trip> findByRouteId(int routeId);

    Optional<Trip> findByBusId(int busId);

    Optional<Trip> findByTripDate(Instant tripDate);

    List<Trip> findByRoute_FromCity(String fromCity);

    List<Trip> findByRoute_ToCity(String toCity);

    List<Trip> findByBus_Type(String bus_Type);

    List<Trip> findByRoute_FromCityAndRoute_ToCity(String fromCity, String toCity);

    List<Trip> findByRoute_FromCityAndRoute_ToCityAndTripDate(String fromCity, String toCity, Instant tripDate);

    List<Trip> findByRoute_FromCityAndRoute_ToCityAndBus_Type(String fromCity, String toCity, String bus_Type);
}
