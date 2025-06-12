package com.team4.backend.controller;

import com.team4.backend.dto.request.BookingRequestDto;
import com.team4.backend.dto.response.BookingResponseDto;
import com.team4.backend.entities.Booking;
import com.team4.backend.mapper.BookingMapper;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.repository.BookingRepository;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingController(BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<BookingResponseDto>>> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingResponseDto> bookingResponseDtos = bookings.stream()
                .map(bookingMapper::toResponseDto)
                .toList();

        return new ResponseEntity<>(ApiResponse.success(bookingResponseDtos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingResponseDto>> getBookingById(@PathVariable Integer id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.map(value -> new ResponseEntity<>(
                        ApiResponse.success(bookingMapper.toResponseDto(value)), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(
                        ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Booking with ID " + id + " not found"),
                        HttpStatus.NOT_FOUND
                ));
    }

    @GetMapping("/trip_id/{trip_id}")
    public ResponseEntity<ApiResponse<List<BookingResponseDto>>> getBookingsByTripId(@PathVariable("trip_id") Integer tripId) {
        List<Booking> bookings = bookingRepository.findByTrip_Id(tripId);
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No bookings found for trip ID " + tripId), HttpStatus.NOT_FOUND);
        }
        List<BookingResponseDto> dtos = bookings.stream().map(bookingMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(dtos), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<BookingResponseDto>>> getBookingsByStatus(@PathVariable("status") String status) {
        List<Booking> bookings = bookingRepository.findByStatus(status);
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No bookings found with status " + status), HttpStatus.NOT_FOUND);
        }
        List<BookingResponseDto> dtos = bookings.stream().map(bookingMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(dtos), HttpStatus.OK);
    }

    @GetMapping("/seat/{seatNumber}")
    public ResponseEntity<ApiResponse<List<BookingResponseDto>>> getBookingsBySeatNumber(@PathVariable("seatNumber") Integer seatNumber) {
        List<Booking> bookings = bookingRepository.findBySeatNumber(seatNumber);
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No bookings found with seat number " + seatNumber), HttpStatus.NOT_FOUND);
        }
        List<BookingResponseDto> dtos = bookings.stream().map(bookingMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(dtos), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<BookingResponseDto>> addBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto) {
        if (bookingRequestDto.id() != null && bookingRepository.existsById(bookingRequestDto.id())) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Booking with ID " + bookingRequestDto.id() + " already exists"),
                    HttpStatus.BAD_REQUEST
            );
        }

        Booking booking = bookingRepository.save(bookingMapper.toEntity(bookingRequestDto));
        return new ResponseEntity<>(ApiResponse.success(HttpStatus.CREATED.value(), "Booking Created", bookingMapper.toResponseDto(booking)), HttpStatus.CREATED);
    }

    @PutMapping("/{booking_id}")
    public ResponseEntity<ApiResponse<BookingResponseDto>> updateBooking(
            @PathVariable("booking_id") Integer bookingId,
            @Valid @RequestBody BookingRequestDto bookingRequestDto) {

        if (!bookingRepository.existsById(bookingId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Booking not found"));
        }

        Booking booking = bookingMapper.toEntity(bookingRequestDto);
        booking.setId(bookingId);
        Booking updated = bookingRepository.save(booking);

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Booking updated successfully", bookingMapper.toResponseDto(updated)));
    }
}
