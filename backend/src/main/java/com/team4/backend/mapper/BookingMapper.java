package com.team4.backend.mapper;

import com.team4.backend.dto.request.BookingRequestDto;
import com.team4.backend.dto.response.BookingResponseDto;
import com.team4.backend.entities.Booking;
import com.team4.backend.mapper.helper.BookingMapperHelper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {BookingMapperHelper.class,
                TripMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookingMapper {

    @Mapping(source = "tripId", target = "trip", qualifiedByName = "tripFromId")
    Booking toEntity(BookingRequestDto bookingRequestDto);

    @Mapping(source = "trip.id", target = "tripId")
    BookingRequestDto toDto(Booking booking);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "tripId", target = "trip", qualifiedByName = "tripFromId")
    Booking partialUpdate(BookingRequestDto bookingRequestDto, @MappingTarget Booking booking);

    BookingResponseDto toResponseDto(Booking booking);
}