package com.team4.backend.mapper.helper;

import com.team4.backend.entities.Address;
import com.team4.backend.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperHelper {

    private final AddressRepository addressRepository;

    public CustomerMapperHelper(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Named("addressFromId")
    public Address addressFromId(Integer id) {
        if (id == null) return null;
        return addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + id));
    }
}
