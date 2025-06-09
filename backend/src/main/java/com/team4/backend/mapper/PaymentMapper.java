package com.team4.backend.mapper;

import com.team4.backend.dto.response.PaymentResponseDto;
import com.team4.backend.entities.Payment;
import com.team4.backend.dto.request.PaymentRequestDto;
import com.team4.backend.mapper.helper.PaymentMapperHelper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {PaymentMapperHelper.class,
                BookingMapper.class,
                CustomerMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PaymentMapper {

    @Mapping(source = "bookingId", target = "booking", qualifiedByName = "bookingFromId")
    @Mapping(source = "customerId", target = "customer", qualifiedByName = "customerFromId")
    Payment toEntity(PaymentRequestDto paymentRequestDto);

    @Mapping(source = "booking.id", target = "bookingId")
    @Mapping(source = "customer.id", target = "customerId")
    PaymentRequestDto toDto(Payment payment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "bookingId", target = "booking", qualifiedByName = "bookingFromId")
    @Mapping(source = "customerId", target = "customer", qualifiedByName = "customerFromId")
    Payment partialUpdate(PaymentRequestDto paymentRequestDto, @MappingTarget Payment payment);

    PaymentResponseDto toResponseDto(Payment payment);
}
