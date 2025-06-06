package com.team4.backend.mapper;

import com.team4.backend.entities.Route;
import com.team4.backend.dto.RouteDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RouteMapper {
    Route toEntity(RouteDto routeDto);

    RouteDto toDto(Route route);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Route partialUpdate(RouteDto routeDto, @MappingTarget Route route);
}