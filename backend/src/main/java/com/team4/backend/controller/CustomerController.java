package com.team4.backend.controller;

import com.team4.backend.dto.request.CustomerRequestDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.dto.response.CustomerResponseDto;
import com.team4.backend.dto.response.PagedResponse;
import com.team4.backend.entities.Customer;
import com.team4.backend.mapper.CustomerMapper;
import com.team4.backend.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<PagedResponse<CustomerResponseDto>>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        List<CustomerResponseDto> dtos = customerPage.getContent()
                .stream()
                .map(customerMapper::toResponseDto)
                .toList();

        PagedResponse<CustomerResponseDto> pagedResponse = new PagedResponse<>(
                dtos,
                customerPage.getNumber(),
                customerPage.getSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isLast()
        );

        return ResponseEntity.ok(ApiResponse.success(pagedResponse));
    }


    @GetMapping("/{customer_id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> getCustomerById(@PathVariable("customer_id") int customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            CustomerResponseDto dto = customerMapper.toResponseDto(customer.get());
            return ResponseEntity.ok(ApiResponse.success(dto));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Customer with ID " + customerId + " not found"));
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
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> getCustomerByAddress(@PathVariable("address_id") int addressId) {
        List<Customer> customers = customerRepository.findByAddress_Id(addressId);

        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Customers with Address ID " + addressId + " not found"));
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
