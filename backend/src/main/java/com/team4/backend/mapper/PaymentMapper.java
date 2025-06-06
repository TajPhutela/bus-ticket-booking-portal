package com.team4.backend.mapper;

import com.team4.backend.entities.Payment;
import com.team4.backend.dto.PaymentDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)public interface PaymentMapper {
    Payment toEntity(PaymentDto paymentDto);

    PaymentDto toDto(Payment payment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)Payment partialUpdate(PaymentDto paymentDto, @MappingTarget Payment payment);
}