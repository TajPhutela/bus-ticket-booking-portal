package com.team4.backend.repository;

import com.team4.backend.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findByRouteId(int routeId);

    List<Trip> findByBusId(int busId);

    List<Trip> findByTripDate(Instant tripDate);

    List<Trip> findByRoute_FromCity(String fromCity);

    List<Trip> findByRoute_ToCity(String toCity);

    List<Trip> findByBus_Type(String bus_Type);

    List<Trip> findByRoute_FromCityAndRoute_ToCity(String fromCity, String toCity);

    List<Trip> findByRoute_FromCityAndRoute_ToCityAndTripDate(String fromCity, String toCity, Instant tripDate);

    List<Trip> findByRoute_FromCityAndRoute_ToCityAndTripDateAndBus_Type(String fromCity, String toCity, Instant tripDate, String bus_Type);
}
