package com.team4.backend.mapper.helper;

import com.team4.backend.entities.Customer;
import com.team4.backend.entities.Trip;
import com.team4.backend.repository.CustomerRepository;
import com.team4.backend.repository.TripRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapperHelper {

    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;

    public ReviewMapperHelper(CustomerRepository customerRepository, TripRepository tripRepository) {
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
    }

    @Named("customerFromId")
    public Customer customerFromId(Integer id) {
        if (id == null) return null;
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    @Named("tripFromId")
    public Trip tripFromId(Integer id) {
        if (id == null) return null;
        return tripRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found with id: " + id));
    }
}
