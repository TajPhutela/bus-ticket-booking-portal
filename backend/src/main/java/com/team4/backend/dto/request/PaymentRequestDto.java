package com.team4.backend.dto.request;

import com.team4.backend.entities.Payment;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link Payment}
 */
public record PaymentRequestDto(
        Integer id,
        @NotNull BookingRequestDto booking,
        Integer customerId,
        BigDecimal amount,
        Instant paymentDate,
        String paymentStatus
) implements Serializable {}
