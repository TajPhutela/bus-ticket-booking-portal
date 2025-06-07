package com.team4.backend.mapper;

import com.team4.backend.dto.AgencyDto;
import com.team4.backend.entities.Agency;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AgencyMapper {
    Agency toEntity(AgencyDto agencyDto);

    AgencyDto toDto(Agency agency);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Agency partialUpdate(AgencyDto agencyDto, @MappingTarget Agency agency);
}