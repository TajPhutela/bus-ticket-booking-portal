package com.team4.backend.dto;

import com.team4.backend.entities.Payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link Payment}
 */
public record PaymentDto(Integer id, BigDecimal amount, Instant paymentDate, String paymentStatus) implements Serializable {
  }