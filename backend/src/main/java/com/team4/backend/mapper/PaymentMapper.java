package com.team4.backend.mapper;

import com.team4.backend.entities.Payment;
import com.team4.backend.dto.PaymentDto;
import com.team4.backend.mapper.helper.PaymentMapperHelper;
import org.mapstruct.*;

import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = PaymentMapperHelper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PaymentMapper {

    @Mapping(source = "bookingId", target = "booking", qualifiedByName = "bookingFromId")
    @Mapping(source = "customerId", target = "customer", qualifiedByName = "customerFromId")
    Payment toEntity(PaymentDto paymentDto);

    @Mapping(source = "booking.id", target = "bookingId")
    @Mapping(source = "customer.id", target = "customerId")
    PaymentDto toDto(Payment payment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "bookingId", target = "booking", qualifiedByName = "bookingFromId")
    @Mapping(source = "customerId", target = "customer", qualifiedByName = "customerFromId")
    Payment partialUpdate(PaymentDto paymentDto, @MappingTarget Payment payment);
}
