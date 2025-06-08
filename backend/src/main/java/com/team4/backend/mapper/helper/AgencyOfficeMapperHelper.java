package com.team4.backend.mapper.helper;

import com.team4.backend.entities.Address;
import com.team4.backend.entities.Agency;
import com.team4.backend.repository.AgencyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class AgencyOfficeMapperHelper {

    private final AgencyRepository agencyRepository;
//    private final AddressRepository addressRepository;

    public AgencyOfficeMapperHelper(
            AgencyRepository agencyRepository
//          AddressRepository addressRepository
    ) {
        this.agencyRepository = agencyRepository;
//        this.addressRepository = addressRepository;
    }

    @Named("agencyFromId")
    public Agency agencyFromId(Integer id) {
        if (id == null) return null;
        return agencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found with id: " + id));
    }

//    @Named("addressFromId")
//    public Address addressFromId(Integer id) {
//        if (id == null) return null;
//        return addressRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + id));
//    }
}
