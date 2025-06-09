package com.team4.backend.mapper;

import com.team4.backend.dto.request.DriverRequestDto;
import com.team4.backend.dto.response.DriverResponseDto;
import com.team4.backend.entities.Driver;
import com.team4.backend.mapper.helper.AgencyOfficeMapperHelper;
import com.team4.backend.mapper.helper.DriverMapperHelper;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AgencyOfficeMapperHelper.class,
                DriverMapperHelper.class,
                AgencyOfficeMapper.class,
                AddressMapper.class})
public interface DriverMapper {
    @Mapping(source = "officeId", target = "office", qualifiedByName = "agencyOfficeFromId")
    @Mapping(source = "addressId", target = "address", qualifiedByName = "addressFromId")
    Driver toEntity(DriverRequestDto driverRequestDto);

    @Mapping(source = "office.id", target = "officeId")
    @Mapping(source = "address.id", target = "addressId")
    DriverRequestDto toDto(Driver driver);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "officeId", target = "office", qualifiedByName = "agencyOfficeFromId")
    @Mapping(source = "addressId", target = "address", qualifiedByName = "addressFromId")
    Driver partialUpdate(DriverRequestDto driverRequestDto, @MappingTarget Driver driver);

    DriverResponseDto toResponseDto(Driver driver);
}