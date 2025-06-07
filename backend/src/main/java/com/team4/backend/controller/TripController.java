package com.team4.backend.controller;

import com.team4.backend.dto.TripDto;
import com.team4.backend.entities.Trip;
import com.team4.backend.mapper.TripMapper;
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
    private final TripMapper tripMapper;

    public TripController(TripRepository tripRepository,
                          TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<TripDto>> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();

        List<TripDto> tripDtos = trips.stream().map(tripMapper::toDto).toList();

        return new ResponseEntity<>(tripDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDto> getTripById(@PathVariable int id) {
        Optional<Trip> trip = tripRepository.findById(id);
        if (trip.isPresent()) {
            TripDto tripDto = tripMapper.toDto(trip.get());
            return new ResponseEntity<>(tripDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
