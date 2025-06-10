package com.team4.backend.controller;

import com.team4.backend.dto.request.CustomerRequestDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.dto.response.CustomerResponseDto;
import com.team4.backend.entities.Customer;
import com.team4.backend.mapper.CustomerMapper;
import com.team4.backend.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponseDto> dtos = customers.stream()
                .map(customerMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/{customer_id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> getCustomerById(@PathVariable("customer_id") int customer_id) {
        Optional<Customer> customer = customerRepository.findById(customer_id);
        if (customer.isPresent()) {
            CustomerResponseDto dto = customerMapper.toResponseDto(customer.get());
            return ResponseEntity.ok(ApiResponse.success(dto));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Customer with ID " + customer_id + " not found"));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> getCustomerByName(@PathVariable String name) {
        List<Customer> customers = customerRepository.findByName(name);
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Customers with name '" + name + "' not found"));
        }

        List<CustomerResponseDto> dtos = customers.stream().map(customerMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> getCustomerByEmail(@PathVariable String email) {
        List<Customer> customers = customerRepository.findByEmail(email);
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Customers with email '" + email + "' not found"));
        }

        List<CustomerResponseDto> dtos = customers.stream().map(customerMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        List<Customer> customers = customerRepository.findByPhone(phoneNumber);
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Customers with phone number '" + phoneNumber + "' not found"));
        }

        List<CustomerResponseDto> dtos = customers.stream().map(customerMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/addressId/{address_id}")
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> getCustomerByAddress(@PathVariable int address_id) {
        List<Customer> customers = customerRepository.findByAddress_Id(address_id);

        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Customers with Address ID " + address_id + " not found"));
        }

        List<CustomerResponseDto> dtos = customers.stream().map(customerMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> addCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        if (customerRequestDto.id() != null && customerRepository.existsById(customerRequestDto.id())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Customer with ID " + customerRequestDto.id() + " already exists"));
        }

        Customer saved = customerRepository.save(customerMapper.toEntity(customerRequestDto));
        CustomerResponseDto dto = customerMapper.toResponseDto(saved);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED.value(), "Customer Created", dto));
    }

    @PutMapping("/{customer_id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> updateCustomer(
            @PathVariable("customer_id") Integer customerId,
            @Valid @RequestBody CustomerRequestDto customerRequestDto) {

        if (!customerRepository.existsById(customerId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Customer not found"));
        }

        Customer customer = customerMapper.toEntity(customerRequestDto);
        customer.setId(customerId);
        Customer updated = customerRepository.save(customer);

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Customer updated successfully", customerMapper.toResponseDto(updated)));
    }
}
