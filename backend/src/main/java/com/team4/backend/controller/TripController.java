package com.team4.backend.controller;

import com.team4.backend.dto.TripDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.entities.Trip;
import com.team4.backend.mapper.TripMapper;
import com.team4.backend.repository.TripRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
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
    public ResponseEntity<ApiResponse<List<TripDto>>> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();

        List<TripDto> tripDtos = trips.stream().map(tripMapper::toDto).toList();

        return new ResponseEntity<>(ApiResponse.success(tripDtos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TripDto>> getTripById(@PathVariable int id) {
        Optional<Trip> trip = tripRepository.findById(id);
        if (trip.isPresent()) {
            TripDto tripDto = tripMapper.toDto(trip.get());
            return new ResponseEntity<>(ApiResponse.success(tripDto), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Trip with Id " + id + " not found"),
                HttpStatus.NOT_FOUND);
    }

    @GetMapping("/routes/{route_id}")
    public ResponseEntity<ApiResponse<List<TripDto>>> getTripsByRouteId(@PathVariable int route_id) {
        List<Trip> trip = tripRepository.findByRouteId(route_id);

        if (trip.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(),
                    "Trips with RouteId " + route_id + " not found"), HttpStatus.NOT_FOUND);
        }

        List<TripDto> tripDtos = trip.stream().map(tripMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripDtos), HttpStatus.OK);
    }

    @GetMapping("/buses/{bus_id}")
    public ResponseEntity<ApiResponse<List<TripDto>>> getTripsByBusId(@PathVariable int bus_id) {
        List<Trip> trip = tripRepository.findByBusId(bus_id);
        if (trip.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Trips with BusId " + bus_id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripDto> tripDtos = trip.stream().map(tripMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripDtos), HttpStatus.OK);
    }

    @GetMapping("/trip_date/{trip_date}")
    public ResponseEntity<ApiResponse<List<TripDto>>> getTripsByTripDate(@PathVariable Instant trip_date) {
        List<Trip> trip = tripRepository.findByTripDate(trip_date);
        if (trip.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Trips on date " + trip_date + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripDto> tripDtos = trip.stream().map(tripMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripDtos), HttpStatus.OK);
    }

    @GetMapping("/from_city/{from_city}")
    public ResponseEntity<ApiResponse<List<TripDto>>> getTripsByFromCity(@PathVariable String from_city) {
        List<Trip> trips = tripRepository.findByRoute_FromCity(from_city);
        if (trips.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Trips from city " + from_city + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripDto> tripDtos = trips.stream().map(tripMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripDtos), HttpStatus.OK);
    }

    @GetMapping("/to_city/{to_city}")
    public ResponseEntity<ApiResponse<List<TripDto>>> getTripsByToCity(@PathVariable String to_city) {
        List<Trip> trips = tripRepository.findByRoute_ToCity(to_city);
        if (trips.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Trips to city " + to_city + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripDto> tripDtos = trips.stream().map(tripMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripDtos), HttpStatus.OK);
    }

    @GetMapping("/bus_type/{bus_type}")
    public ResponseEntity<ApiResponse<List<TripDto>>> getTripsByBusType(@PathVariable String bus_type) {
        List<Trip> trips = tripRepository.findByBus_Type(bus_type);
        if (trips.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Trips with bus type " + bus_type + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripDto> tripDtos = trips.stream().map(tripMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripDtos), HttpStatus.OK);
    }

    @GetMapping("/{from_city}/{to_city}/{trip_date}")
    public ResponseEntity<ApiResponse<List<TripDto>>> getTripsByFromToCityAndDate(@PathVariable String from_city,
                                                                     @PathVariable String to_city,
                                                                     @PathVariable Instant trip_date) {
        List<Trip> trips = tripRepository.findByRoute_FromCityAndRoute_ToCityAndTripDate(from_city, to_city, trip_date);
        if (trips.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(),
                            "Trips from city " + from_city + " to " + to_city + " on date " + trip_date + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripDto> tripDtos = trips.stream().map(tripMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripDtos), HttpStatus.OK);
    }

    @GetMapping("/{from_city}/{to_city}")
    public ResponseEntity<ApiResponse<List<TripDto>>> getTripsByFromToCityAndDate(@PathVariable String from_city,
                                                                     @PathVariable String to_city) {
        List<Trip> trips = tripRepository.findByRoute_FromCityAndRoute_ToCity(from_city, to_city);
        if (trips.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(),
                            "Trips from city " + from_city + " to " + to_city + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripDto> tripDtos = trips.stream().map(tripMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripDtos), HttpStatus.OK);
    }

    @GetMapping("/{from_city}/{to_city}/{trip_date}/{bus_type}")
    public ResponseEntity<ApiResponse<List<TripDto>>> getTripsByFromToCityAndDate(@PathVariable String from_city,
                                                                     @PathVariable String to_city,
                                                                     @PathVariable Instant trip_date,
                                                                     @PathVariable String bus_type ){
        List<Trip> trips = tripRepository.findByRoute_FromCityAndRoute_ToCityAndTripDateAndBus_Type(from_city, to_city, trip_date, bus_type);
        if (trips.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(),
                            "Trips from city " + from_city + " to " + to_city + " on date " + trip_date + " with bus type " + bus_type + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripDto> tripDtos = trips.stream().map(tripMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripDtos), HttpStatus.OK);
    }
}
