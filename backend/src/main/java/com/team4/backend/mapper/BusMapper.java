package com.team4.backend.mapper;

import com.team4.backend.dto.BusDto;
import com.team4.backend.entities.Bus;
import com.team4.backend.entities.AgencyOffice;

public class BusMapper {

    public static BusDto toDTO(Bus bus) {
        if (bus == null) return null;

        BusDto dto = new BusDto();
        dto.setId(bus.getId());
        dto.setOfficeId(bus.getOffice() != null ? bus.getOffice().getId() : null);
        dto.setRegistrationNumber(bus.getRegistrationNumber());
        dto.setCapacity(bus.getCapacity());
        dto.setType(bus.getType());
        return dto;
    }

    public static Bus toEntity(BusDto dto, AgencyOffice office) {
        if (dto == null || office == null) return null;

        Bus bus = new Bus();
        bus.setId(dto.getId());
        bus.setOffice(office);
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setCapacity(dto.getCapacity());
        bus.setType(dto.getType());
        return bus;
    }
}

