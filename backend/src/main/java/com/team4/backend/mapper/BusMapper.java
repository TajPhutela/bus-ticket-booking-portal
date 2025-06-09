package com.team4.backend.mapper;

import com.team4.backend.entities.Bus;
import com.team4.backend.dto.BusDto;
import com.team4.backend.mapper.helper.BusMapperHelper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = BusMapperHelper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BusMapper {

    @Mapping(source = "officeId", target = "office", qualifiedByName = "officeFromId")
    Bus toEntity(BusDto busDto);

    @Mapping(source = "office.id", target = "officeId")
    BusDto toDto(Bus bus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "officeId", target = "office", qualifiedByName = "officeFromId")
    Bus partialUpdate(BusDto busDto, @MappingTarget Bus bus);
}