package com.team4.backend.controller;

import com.team4.backend.dto.PaymentDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.entities.Payment;
import com.team4.backend.mapper.PaymentMapper;
import com.team4.backend.repository.PaymentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentController(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<PaymentDto>> addPayment(@Valid @RequestBody PaymentDto paymentDto) {
        Payment payment = paymentRepository.save(paymentMapper.toEntity(paymentDto));
        return new ResponseEntity<>(
                ApiResponse.success(HttpStatus.CREATED.value(), "Payment Created", paymentMapper.toDto(payment)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentDto>> updatePayment(
            @PathVariable Integer id,
            @Valid @RequestBody PaymentDto paymentDto) {

        if (!paymentRepository.existsById(id)) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Payment with ID " + id + " not found"),
                    HttpStatus.NOT_FOUND
            );
        }

        Payment payment = paymentMapper.toEntity(paymentDto);
        payment.setId(id);
        Payment saved = paymentRepository.save(payment);

        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "Payment updated successfully", paymentMapper.toDto(saved))
        );
    }


    @GetMapping("")
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentDto> paymentDtos = payments.stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ApiResponse.success(paymentDtos), HttpStatus.OK);
    }

    @GetMapping("/{payment_id}")
    public ResponseEntity<ApiResponse<PaymentDto>> getPaymentById(@PathVariable("payment_id") Integer paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (payment.isPresent()) {
            PaymentDto paymentDto = paymentMapper.toDto(payment.get());
            return new ResponseEntity<>(ApiResponse.success(paymentDto), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Payment with Id " + paymentId + " not found"),
                HttpStatus.NOT_FOUND);
    }

    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getPaymentsByCustomerId(@PathVariable("customer_id") Integer customerId) {
        List<Payment> payments = paymentRepository.findByCustomerId(customerId);
        if (payments.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Payments for customer " + customerId + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<PaymentDto> paymentDtos = payments.stream().map(paymentMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(ApiResponse.success(paymentDtos), HttpStatus.OK);
    }

    @GetMapping("/booking/{booking_id}")
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getPaymentsByBookingId(@PathVariable("booking_id") Integer bookingId) {
        List<Payment> payments = paymentRepository.findByBookingId(bookingId);
        if (payments.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Payments for booking " + bookingId + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<PaymentDto> paymentDtos = payments.stream().map(paymentMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(ApiResponse.success(paymentDtos), HttpStatus.OK);
    }

    @GetMapping("/amount/{amount}")
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getPaymentsByAmount(@PathVariable("amount") BigDecimal amount) {
        List<Payment> payments = paymentRepository.findByAmount(amount);
        if (payments.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Payments with amount " + amount + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<PaymentDto> paymentDtos = payments.stream().map(paymentMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(ApiResponse.success(paymentDtos), HttpStatus.OK);
    }

    @GetMapping("/status/{payment_status}")
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getPaymentsByStatus(@PathVariable("payment_status") String paymentStatus) {
        List<Payment> payments = paymentRepository.findByPaymentStatus(paymentStatus);
        if (payments.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Payments with status " + paymentStatus + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        List<PaymentDto> paymentDtos = payments.stream().map(paymentMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(ApiResponse.success(paymentDtos), HttpStatus.OK);
    }
}
