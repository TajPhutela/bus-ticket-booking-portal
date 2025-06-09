package com.team4.backend.mapper;

import com.team4.backend.dto.request.AgencyRequestDto;
import com.team4.backend.entities.Agency;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AgencyMapper {
    Agency toEntity(AgencyRequestDto agencyRequestDto);

    AgencyRequestDto toDto(Agency agency);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Agency partialUpdate(AgencyRequestDto agencyRequestDto, @MappingTarget Agency agency);
}