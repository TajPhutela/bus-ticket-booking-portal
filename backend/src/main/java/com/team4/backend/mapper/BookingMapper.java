package com.team4.backend.mapper;

import com.team4.backend.dto.BookingDto;
import com.team4.backend.entities.Booking;
import com.team4.backend.mapper.helper.BookingMapperHelper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = BookingMapperHelper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookingMapper {

    @Mapping(source = "tripId", target = "trip", qualifiedByName = "tripFromId")
    Booking toEntity(BookingDto bookingDto);

    @Mapping(source = "trip.id", target = "tripId")
    BookingDto toDto(Booking booking);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "tripId", target = "trip", qualifiedByName = "tripFromId")
    Booking partialUpdate(BookingDto bookingDto, @MappingTarget Booking booking);
}