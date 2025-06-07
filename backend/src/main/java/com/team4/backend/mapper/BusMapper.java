package com.team4.backend.mapper;

import com.team4.backend.entities.Bus;
import com.team4.backend.dto.BusDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BusMapper {
    Bus toEntity(BusDto busDto);

    BusDto toDto(Bus bus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Bus partialUpdate(BusDto busDto, @MappingTarget Bus bus);
}