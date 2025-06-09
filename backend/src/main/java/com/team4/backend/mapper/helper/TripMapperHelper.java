package com.team4.backend.mapper.helper;

import com.team4.backend.entities.Bus;
import com.team4.backend.entities.Route;
import com.team4.backend.repository.BusRepository;
import com.team4.backend.repository.routeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class TripMapperHelper {

    private final routeRepository routeRepository;
    private final BusRepository busRepository;
//    private final DriverRepository driverRepository;

    public TripMapperHelper(routeRepository routeRepository, BusRepository busRepository) {
        this.routeRepository = routeRepository;
        this.busRepository = busRepository;
//        this.driverRepository = driverRepository;
    }

    @Named("routeFromId")
    public Route routeFromId(Integer id) {
        return id == null ? null : routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found with id: " + id));
    }

    @Named("busFromId")
    public Bus busFromId(Integer id) {
        return id == null ? null : busRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus not found with id: " + id));
    }

//    @Named("driverFromId")
//    public Driver driverFromId(Integer id) {
//        return id == null ? null : driverRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + id));
//    }
}

