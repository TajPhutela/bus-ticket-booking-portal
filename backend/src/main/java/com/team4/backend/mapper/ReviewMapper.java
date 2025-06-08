package com.team4.backend.mapper;

import com.team4.backend.entities.Review;
import com.team4.backend.dto.ReviewDto;
import com.team4.backend.mapper.helper.ReviewMapperHelper;
import org.mapstruct.*;

import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = ReviewMapperHelper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewMapper {

    @Mapping(source = "customerId", target = "customer", qualifiedByName = "customerFromId")
    @Mapping(source = "tripId", target = "trip", qualifiedByName = "tripFromId")
    Review toEntity(ReviewDto reviewDto);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "trip.id", target = "tripId")
    ReviewDto toDto(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "customerId", target = "customer", qualifiedByName = "customerFromId")
    @Mapping(source = "tripId", target = "trip", qualifiedByName = "tripFromId")
    Review partialUpdate(ReviewDto reviewDto, @MappingTarget Review review);
}
