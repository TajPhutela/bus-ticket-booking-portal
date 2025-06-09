package com.team4.backend.controller;

import com.team4.backend.dto.request.CustomerRequestDto;
import com.team4.backend.dto.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<CustomerRequestDto>>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerRequestDto> customerRequestDtos = customers.stream()
                .map(customerMapper::toDto)
                .toList();
        return new ResponseEntity<>(ApiResponse.success(customerRequestDtos), HttpStatus.OK);
    }

    @GetMapping("/{customer_id}")
    public ResponseEntity<ApiResponse<CustomerRequestDto>> getCustomerById(@PathVariable("customer_id") int customer_id) {
        Optional<Customer> customer = customerRepository.findById(customer_id);
        if (customer.isPresent()) {
            CustomerRequestDto customerRequestDto = customerMapper.toDto(customer.get());
            return new ResponseEntity<>(ApiResponse.success(customerRequestDto), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Customer with ID " + customer_id + " not found"),
                HttpStatus.NOT_FOUND);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<List<CustomerRequestDto>>> getCustomerByName(@PathVariable String name) {
        List<Customer> customers = customerRepository.findByName(name);
        if (customers.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Customers with name '" + name + "' not found"),
                    HttpStatus.NOT_FOUND);
        }

        List<CustomerRequestDto> customerRequestDtos = customers.stream().map(customerMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(customerRequestDtos), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<List<CustomerRequestDto>>> getCustomerByEmail(@PathVariable String email) {
        List<Customer> customers = customerRepository.findByEmail(email);
        if (customers.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Customers with email '" + email + "' not found"),
                    HttpStatus.NOT_FOUND);
        }

        List<CustomerRequestDto> customerRequestDtos = customers.stream().map(customerMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(customerRequestDtos), HttpStatus.OK);
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    public ResponseEntity<ApiResponse<List<CustomerRequestDto>>> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        List<Customer> customers = customerRepository.findByPhone(phoneNumber);
        if (customers.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(),
                    "Customers with phone number '" + phoneNumber + "' not found"),
                    HttpStatus.NOT_FOUND);
        }

        List<CustomerRequestDto> customerRequestDtos = customers.stream().map(customerMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(customerRequestDtos), HttpStatus.OK);
    }

    @GetMapping("/addressId/{address_id}")
    public ResponseEntity<ApiResponse<List<CustomerRequestDto>>> getCustomerByAddress(@PathVariable int address_id) {
        List<Customer> customers = customerRepository.findByAddress_Id(address_id);

        if (customers.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(),
                    "Customers with Address ID " + address_id + " not found"),
                    HttpStatus.NOT_FOUND);
        }

        List<CustomerRequestDto> customerRequestDtos = customers.stream().map(customerMapper::toDto).toList();
        return new ResponseEntity<>(ApiResponse.success(customerRequestDtos), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CustomerRequestDto>> addCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        Customer customer = customerRepository.save(customerMapper.toEntity(customerRequestDto));
        CustomerRequestDto savedCustomerRequestDto = customerMapper.toDto(customer);

        return new ResponseEntity<>(
                ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Customer Created",
                        savedCustomerRequestDto
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{customer_id}")
    public ResponseEntity<ApiResponse<CustomerRequestDto>> updateCustomer(
            @PathVariable("customer_id") Integer customerId,
            @Valid @RequestBody CustomerRequestDto customerRequestDto) {

        if (!customerRepository.existsById(customerId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Customer not found"));
        }

        Customer customer = customerMapper.toEntity(customerRequestDto);
        customer.setId(customerId);
        Customer updated = customerRepository.save(customer);

        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(),
                        "Customer updated successfully",
                        customerMapper.toDto(updated))
        );
    }

}