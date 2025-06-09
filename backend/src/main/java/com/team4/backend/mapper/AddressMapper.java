package com.team4.backend.mapper;

import com.team4.backend.dto.request.AddressRequestDto;
import com.team4.backend.dto.response.AddressResponseDto;
import com.team4.backend.entities.Address;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    Address toEntity(AddressRequestDto addressRequestDto);

    AddressRequestDto toDto(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address partialUpdate(AddressRequestDto addressRequestDto, @MappingTarget Address address);

    AddressResponseDto toResponseDto(Address address);
}