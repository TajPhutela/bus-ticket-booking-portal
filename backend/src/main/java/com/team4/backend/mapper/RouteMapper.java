package com.team4.backend.mapper;

import com.team4.backend.dto.request.RouteRequestDto;
import com.team4.backend.entities.Route;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RouteMapper {
    Route toEntity(RouteRequestDto routeRequestDto);

    RouteRequestDto toDto(Route route);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Route partialUpdate(RouteRequestDto routeRequestDto, @MappingTarget Route route);
}