package com.team4.backend.mapper.helper;

import com.team4.backend.entities.AgencyOffice;
import com.team4.backend.repository.AgencyOfficeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class DriverMapperHelper {
    private final AgencyOfficeRepository agencyOfficeRepository;

    public DriverMapperHelper(AgencyOfficeRepository agencyOfficeRepository) {
        this.agencyOfficeRepository = agencyOfficeRepository;
    }

    @Named("agencyOfficeFromId")
    public AgencyOffice agencyOfficeFromId(Integer id) {
        if (id == null) return null;
        return agencyOfficeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agency Office not found with id: " + id));
    }
}
