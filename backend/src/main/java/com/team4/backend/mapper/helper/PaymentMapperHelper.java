package com.team4.backend.mapper.helper;

import com.team4.backend.dto.request.BookingRequestDto;
import com.team4.backend.entities.Booking;
import com.team4.backend.entities.Customer;
import com.team4.backend.entities.Trip;
import com.team4.backend.repository.BookingRepository;
import com.team4.backend.repository.CustomerRepository;
import com.team4.backend.repository.TripRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperHelper {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;

    public PaymentMapperHelper(
            BookingRepository bookingRepository,
            CustomerRepository customerRepository,
            TripRepository tripRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
    }

    @Named("bookingFromId")
    public Booking bookingFromId(Integer id) {
        if (id == null) return null;
        return bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));
    }

    @Named("customerFromId")
    public Customer customerFromId(Integer id) {
        if (id == null) return null;
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    @Named("bookingFromDto")
    public Booking bookingFromDto(BookingRequestDto dto) {
        if (dto == null) return null;

        Booking booking = new Booking();
        booking.setId(dto.id());
        booking.setSeatNumber(dto.seatNumber());
        booking.setStatus(dto.status());

        if (dto.tripId() != null) {
            Trip trip = tripRepository.getReferenceById(dto.tripId());
            booking.setTrip(trip);
        }

        return booking;
    }
}
