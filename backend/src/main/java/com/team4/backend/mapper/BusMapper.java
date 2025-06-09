package com.team4.backend.mapper;

import com.team4.backend.dto.request.BusRequestDto;
import com.team4.backend.dto.response.BusResponseDto;
import com.team4.backend.entities.Bus;
import com.team4.backend.mapper.helper.BusMapperHelper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {BusMapperHelper.class,
                AgencyOfficeMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BusMapper {

    @Mapping(source = "officeId", target = "office", qualifiedByName = "officeFromId")
    Bus toEntity(BusRequestDto busRequestDto);

    @Mapping(source = "office.id", target = "officeId")
    BusRequestDto toDto(Bus bus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "officeId", target = "office", qualifiedByName = "officeFromId")
    Bus partialUpdate(BusRequestDto busRequestDto, @MappingTarget Bus bus);

    BusResponseDto toResponseDto(Bus bus);
}