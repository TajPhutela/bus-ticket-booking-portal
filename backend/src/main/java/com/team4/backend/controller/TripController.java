package com.team4.backend.controller;

import com.team4.backend.dto.request.TripRequestDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.dto.response.TripResponseDto;
import com.team4.backend.entities.Trip;
import com.team4.backend.mapper.TripMapper;
import com.team4.backend.repository.TripRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<List<TripResponseDto>>> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();

        List<TripResponseDto> tripResponseDtos = trips.stream().map(tripMapper::toResponseDto).toList();

        return new ResponseEntity<>(ApiResponse.success(tripResponseDtos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TripResponseDto>> getTripById(@PathVariable int id) {
        Optional<Trip> trip = tripRepository.findById(id);
        if (trip.isPresent()) {
            TripResponseDto tripResponseDto = tripMapper.toResponseDto(trip.get());
            return new ResponseEntity<>(ApiResponse.success(tripResponseDto), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Trip with Id " + id + " not found"),
                HttpStatus.NOT_FOUND);
    }

    @GetMapping("/routes/{route_id}")
    public ResponseEntity<ApiResponse<List<TripResponseDto>>> getTripsByRouteId(@PathVariable int route_id) {
        List<Trip> trip = tripRepository.findByRouteId(route_id);

        if (trip.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(),
                    "Trips with RouteId " + route_id + " not found"), HttpStatus.NOT_FOUND);
        }

        List<TripResponseDto> tripRequestDtos = trip.stream().map(tripMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripRequestDtos), HttpStatus.OK);
    }

    @GetMapping("/buses/{bus_id}")
    public ResponseEntity<ApiResponse<List<TripResponseDto>>> getTripsByBusId(@PathVariable int bus_id) {
        List<Trip> trip = tripRepository.findByBusId(bus_id);
        if (trip.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Trips with BusId " + bus_id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripResponseDto> tripResponseDtos = trip.stream().map(tripMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripResponseDtos), HttpStatus.OK);
    }

    @GetMapping("/trip_date/{trip_date}")
    public ResponseEntity<ApiResponse<List<TripResponseDto>>> getTripsByTripDate(@PathVariable Instant trip_date) {
        List<Trip> trip = tripRepository.findByTripDate(trip_date);
        if (trip.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Trips on date " + trip_date + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripResponseDto> tripResponseDtos = trip.stream().map(tripMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripResponseDtos), HttpStatus.OK);
    }

    @GetMapping("/from_city/{from_city}")
    public ResponseEntity<ApiResponse<List<TripResponseDto>>> getTripsByFromCity(@PathVariable String from_city) {
        List<Trip> trips = tripRepository.findByRoute_FromCity(from_city);
        if (trips.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Trips from city " + from_city + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripResponseDto> tripResponseDtos = trips.stream().map(tripMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripResponseDtos), HttpStatus.OK);
    }

    @GetMapping("/to_city/{to_city}")
    public ResponseEntity<ApiResponse<List<TripResponseDto>>> getTripsByToCity(@PathVariable String to_city) {
        List<Trip> trips = tripRepository.findByRoute_ToCity(to_city);
        if (trips.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Trips to city " + to_city + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripResponseDto> tripResponseDtos = trips.stream().map(tripMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripResponseDtos), HttpStatus.OK);
    }

    @GetMapping("/bus_type/{bus_type}")
    public ResponseEntity<ApiResponse<List<TripResponseDto>>> getTripsByBusType(@PathVariable String bus_type) {
        List<Trip> trips = tripRepository.findByBus_Type(bus_type);
        if (trips.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Trips with bus type " + bus_type + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripResponseDto> tripResponseDtos = trips.stream().map(tripMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripResponseDtos), HttpStatus.OK);
    }

    @GetMapping("/{from_city}/{to_city}/{trip_date}")
    public ResponseEntity<ApiResponse<List<TripResponseDto>>> getTripsByFromToCityAndDate(@PathVariable String from_city,
                                                                                         @PathVariable String to_city,
                                                                                         @PathVariable Instant trip_date) {
        List<Trip> trips = tripRepository.findByRoute_FromCityAndRoute_ToCityAndTripDate(from_city, to_city, trip_date);
        if (trips.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(),
                            "Trips from city " + from_city + " to " + to_city + " on date " + trip_date + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripResponseDto> tripResponseDtos = trips.stream().map(tripMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripResponseDtos), HttpStatus.OK);
    }

    @GetMapping("/{from_city}/{to_city}")
    public ResponseEntity<ApiResponse<List<TripResponseDto>>> getTripsByFromToCityAndDate(@PathVariable String from_city,
                                                                                         @PathVariable String to_city) {
        List<Trip> trips = tripRepository.findByRoute_FromCityAndRoute_ToCity(from_city, to_city);
        if (trips.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(),
                            "Trips from city " + from_city + " to " + to_city + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<TripResponseDto> tripResponseDtos = trips.stream().map(tripMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripResponseDtos), HttpStatus.OK);
    }

    @GetMapping("/{from_city}/{to_city}/{trip_date}/{bus_type}")
    public ResponseEntity<ApiResponse<List<TripResponseDto>>> getTripsByFromToCityAndDate(@PathVariable String from_city,
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
        List<TripResponseDto> tripResponseDtos = trips.stream().map(tripMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(tripResponseDtos), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<TripResponseDto>> addTrip(@Valid @RequestBody TripRequestDto tripRequestDto) {
        Trip trip = tripRepository.save(tripMapper.toEntity(tripRequestDto));
        return new ResponseEntity<>(
                ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Trip Created",
                        tripMapper.toResponseDto(trip)
                ),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TripResponseDto>> updateTrip(@PathVariable Integer id,
                                                                  @Valid @RequestBody TripRequestDto tripRequestDto) {
        Optional<Trip> existingTripOpt = tripRepository.findById(id);

        if (existingTripOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "Trip not found"));
        }

        Trip updatedTrip = tripMapper.toEntity(tripRequestDto);
        updatedTrip.setId(id);

        Trip savedTrip = tripRepository.save(updatedTrip);

        return ResponseEntity.ok(ApiResponse.success(tripMapper.toResponseDto(savedTrip)));
    }

}
