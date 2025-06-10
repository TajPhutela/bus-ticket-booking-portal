package com.team4.backend.mapper.helper;

import com.team4.backend.entities.AgencyOffice;
import com.team4.backend.repository.AgencyOfficeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class BusMapperHelper {

    private final AgencyOfficeRepository officeRepository;

    public BusMapperHelper(AgencyOfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @Named("officeFromId")
    public AgencyOffice officeFromId(Integer id) {
        if (id == null) return null;
        return officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AgencyOffice not found with id: " + id));
    }
}
