package com.team4.backend.mapper;

import com.team4.backend.entities.Trip;
import com.team4.backend.dto.TripDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)public interface TripMapper {
    Trip toEntity(TripDto tripDto);

    TripDto toDto(Trip trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)Trip partialUpdate(TripDto tripDto, @MappingTarget Trip trip);
}