package com.team4.backend.entities;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AgencyOfficeMapper {
    AgencyOffice toEntity(AgencyOfficeDto agencyOfficeDto);

    AgencyOfficeDto toDto(AgencyOffice agencyOffice);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AgencyOffice partialUpdate(AgencyOfficeDto agencyOfficeDto, @MappingTarget AgencyOffice agencyOffice);
}