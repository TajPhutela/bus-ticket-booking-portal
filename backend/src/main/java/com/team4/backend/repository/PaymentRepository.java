package com.team4.backend.repository;

import com.team4.backend.entities.Booking;
import com.team4.backend.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByCustomerId(Integer customerId);
    List<Payment> findByBookingId(Integer bookingId);

    List<Payment> findByAmount(BigDecimal paymentId);
    List<Payment> findByPaymentStatus(String paymentStatus);
}
