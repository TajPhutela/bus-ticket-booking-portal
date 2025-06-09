package com.team4.backend.mapper.helper;

import com.team4.backend.entities.Customer;
import com.team4.backend.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperHelper {

//    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;

    public PaymentMapperHelper(
//            BookingRepository bookingRepository,
            CustomerRepository customerRepository
    ) {
//        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
    }

//    @Named("bookingFromId")
//    public Booking bookingFromId(Integer id) {
//        if (id == null) return null;
//        return bookingRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));
//    }

    @Named("customerFromId")
    public Customer customerFromId(Integer id) {
        if (id == null) return null;
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }
}
