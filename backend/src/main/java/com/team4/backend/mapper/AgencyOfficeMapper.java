package com.team4.backend.mapper;

import com.team4.backend.dto.request.AgencyOfficeRequestDto;
import com.team4.backend.entities.AgencyOffice;
import com.team4.backend.mapper.helper.AgencyOfficeMapperHelper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = AgencyOfficeMapperHelper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AgencyOfficeMapper {

    @Mapping(source = "agencyId", target = "agency", qualifiedByName = "agencyFromId")
    @Mapping(source = "officeAddressId", target = "officeAddress", qualifiedByName = "addressFromId")
    AgencyOffice toEntity(AgencyOfficeRequestDto agencyOfficeRequestDto);

    @Mapping(source = "agency.id", target = "agencyId")
    @Mapping(source = "officeAddress.id", target = "officeAddressId")
    AgencyOfficeRequestDto toDto(AgencyOffice agencyOffice);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "agencyId", target = "agency", qualifiedByName = "agencyFromId")
    @Mapping(source = "officeAddressId", target = "officeAddress", qualifiedByName = "addressFromId")
    AgencyOffice partialUpdate(AgencyOfficeRequestDto agencyOfficeRequestDto, @MappingTarget AgencyOffice agencyOffice);
}