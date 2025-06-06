package com.team4.backend.controller;

import com.team4.backend.entities.Trip;
import com.team4.backend.repository.TripRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/trips")
public class TripController {
    private final TripRepository tripRepository;

    public TripController(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable int id) {
        Optional<Trip> trip = tripRepository.findById(id);
        if (trip.isPresent()) {
            return new ResponseEntity<>(trip.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
