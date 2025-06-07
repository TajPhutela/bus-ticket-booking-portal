package com.team4.backend.controller;



import com.team4.backend.dto.PaymentDto;
import com.team4.backend.entities.Booking;
import com.team4.backend.entities.Payment;
import com.team4.backend.mapper.PaymentMapper;
import com.team4.backend.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;



    @GetMapping("")
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentDto> paymentDtos = payments.stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentDtos);
    }


    @GetMapping("/{payment_id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable("payment_id") Integer paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        return payment.map(paymentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByCustomerId(@PathVariable("customer_id") Integer customerId) {
        List<Payment>payments = paymentRepository.findByCustomerId(customerId);
        List<PaymentDto> paymentDtos = payments.stream().map(paymentMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(paymentDtos);
    }

    @GetMapping("/booking/{booking_id}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByBookingId(@PathVariable("booking_id") Integer bookingId) {
        List<Payment>payments = paymentRepository.findByBookingId(bookingId);
        List<PaymentDto> paymentDtos = payments.stream().map(paymentMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(paymentDtos);
    }



}
