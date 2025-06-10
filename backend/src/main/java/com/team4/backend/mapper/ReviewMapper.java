package com.team4.backend.mapper;

import com.team4.backend.dto.request.ReviewRequestDto;
import com.team4.backend.dto.response.ReviewResponseDto;
import com.team4.backend.entities.Review;
import com.team4.backend.mapper.helper.ReviewMapperHelper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ReviewMapperHelper.class,
                CustomerMapper.class,
                TripMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewMapper {

    @Mapping(source = "customerId", target = "customer", qualifiedByName = "customerFromId")
    @Mapping(source = "tripId", target = "trip", qualifiedByName = "tripFromId")
    Review toEntity(ReviewRequestDto reviewRequestDto);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "trip.id", target = "tripId")
    ReviewRequestDto toDto(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "customerId", target = "customer", qualifiedByName = "customerFromId")
    @Mapping(source = "tripId", target = "trip", qualifiedByName = "tripFromId")
    Review partialUpdate(ReviewRequestDto reviewRequestDto, @MappingTarget Review review);

    ReviewResponseDto toResponseDto(Review review);
}
