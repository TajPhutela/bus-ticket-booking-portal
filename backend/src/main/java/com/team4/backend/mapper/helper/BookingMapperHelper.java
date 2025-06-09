package com.team4.backend.mapper.helper;

import com.team4.backend.entities.Trip;
import com.team4.backend.repository.TripRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class BookingMapperHelper {

    private final TripRepository tripRepository;

    public BookingMapperHelper(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Named("tripFromId")
    public Trip tripFromId(Integer id) {
        if (id == null) return null;
        return tripRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found with id: " + id));
    }
}