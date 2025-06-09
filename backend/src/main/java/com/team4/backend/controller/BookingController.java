package com.team4.backend.controller;

import com.team4.backend.dto.request.BookingRequestDto;
import com.team4.backend.entities.Booking;
import com.team4.backend.mapper.BookingMapper;
import com.team4.backend.dto.response.ApiResponse;

import com.team4.backend.repository.BookingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingMapper bookingMapper;


    @GetMapping("")
    public ResponseEntity<ApiResponse<List<BookingRequestDto>>> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingRequestDto> bookingRequestDtos = bookings.stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ApiResponse.success(bookingRequestDtos), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingRequestDto>> getBookingById(@PathVariable Integer id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            BookingRequestDto dto = bookingMapper.toDto(booking.get());
            return new ResponseEntity<>(ApiResponse.success(dto), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Booking with ID " + id + " not found"), HttpStatus.NOT_FOUND);
    }


    @GetMapping("/trip_id/{trip_id}")
    public ResponseEntity<ApiResponse<List<BookingRequestDto>>> getBookingsByTripId(@PathVariable("trip_id") Integer tripId) {
        List<Booking> bookings = bookingRepository.findByTrip_Id(tripId);
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No bookings found for trip ID " + tripId), HttpStatus.NOT_FOUND);
        }

        List<BookingRequestDto> bookingRequestDtos = bookings.stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ApiResponse.success(bookingRequestDtos), HttpStatus.OK);
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<BookingRequestDto>>> getBookingsByStatus(@PathVariable("status") String status) {
        List<Booking> bookings = bookingRepository.findByStatus(status);
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No bookings found with status " + status), HttpStatus.NOT_FOUND);
        }

        List<BookingRequestDto> bookingRequestDtos = bookings.stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ApiResponse.success(bookingRequestDtos), HttpStatus.OK);
    }

    @GetMapping("/seat/{seatNumber}")
    public ResponseEntity<ApiResponse<List<BookingRequestDto>>> getBookingsBySeatNumber(@PathVariable("seatNumber") Integer seatNumber) {
        List<Booking> bookings = bookingRepository.findBySeatNumber(seatNumber);
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No bookings found with seat number " + seatNumber),
                    HttpStatus.NOT_FOUND
            );
        }

        List<BookingRequestDto> bookingRequestDtos = bookings.stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ApiResponse.success(bookingRequestDtos), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<BookingRequestDto>> addBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto) {
        Booking booking = bookingRepository.save(bookingMapper.toEntity(bookingRequestDto));
        return new ResponseEntity<>(
                ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Booking Created",
                        bookingMapper.toDto(booking)
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{booking_id}")
    public ResponseEntity<ApiResponse<BookingRequestDto>> updateBooking(
            @PathVariable("booking_id") Integer bookingId,
            @Valid @RequestBody BookingRequestDto bookingRequestDto) {

        if (!bookingRepository.existsById(bookingId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Booking not found"));
        }

        Booking booking = bookingMapper.toEntity(bookingRequestDto);
        booking.setId(bookingId);

        Booking updated = bookingRepository.save(booking);

        return ResponseEntity.ok(ApiResponse.success(
                HttpStatus.OK.value(),
                "Booking updated successfully",
                bookingMapper.toDto(updated)
        ));
    }

}

