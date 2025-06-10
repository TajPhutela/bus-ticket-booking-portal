package com.team4.backend.mapper;

import com.team4.backend.dto.request.TripRequestDto;
import com.team4.backend.dto.response.TripResponseDto;
import com.team4.backend.entities.Trip;
import com.team4.backend.mapper.helper.TripMapperHelper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TripMapperHelper.class,
                RouteMapper.class,
                BusMapper.class,
                DriverMapper.class})
public interface TripMapper {
    @Mapping(source = "routeId", target = "route", qualifiedByName = "routeFromId")
    @Mapping(source = "busId", target = "bus", qualifiedByName = "busFromId")
    @Mapping(source = "driver1Id", target = "driver1Driver", qualifiedByName = "driverFromId")
    @Mapping(source = "driver2Id", target = "driver2Driver", qualifiedByName = "driverFromId")
    Trip toEntity(TripRequestDto tripRequestDto);

    @Mapping(source = "route.id", target = "routeId")
    @Mapping(source = "bus.id", target = "busId")
    @Mapping(source = "driver1Driver.id", target = "driver1Id")
    @Mapping(source = "driver2Driver.id", target = "driver2Id")
    TripRequestDto toDto(Trip trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "routeId", target = "route", qualifiedByName = "routeFromId")
    @Mapping(source = "busId", target = "bus", qualifiedByName = "busFromId")
    @Mapping(source = "driver1Id", target = "driver1Driver", qualifiedByName = "driverFromId")
    @Mapping(source = "driver2Id", target = "driver2Driver", qualifiedByName = "driverFromId")
    Trip partialUpdate(TripRequestDto tripRequestDto, @MappingTarget Trip trip);


    TripResponseDto toResponseDto(Trip trip);
}