package com.team4.backend.controller;

import com.team4.backend.dto.CustomerDto;
import com.team4.backend.dto.ReviewDto;
import com.team4.backend.dto.TripDto;
import com.team4.backend.entities.Customer;
import com.team4.backend.entities.Review;
import com.team4.backend.entities.Trip;
import com.team4.backend.mapper.CustomerMapper;
import com.team4.backend.mapper.ReviewMapper;
import com.team4.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = customers.stream()
                .map(customer -> customerMapper.toDto(customer))
                .toList();
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @GetMapping("/{customer_id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customer_id") int customer_id) {
        Optional<Customer> customer = customerRepository.findById(customer_id);
        if (customer.isPresent()) {
            CustomerDto customerDto = customerMapper.toDto(customer.get());
            return new ResponseEntity<>(customerDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<CustomerDto>> getCustomerByName(@PathVariable String name) {
        List<Customer> customer = customerRepository.findByName(name);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CustomerDto> customerDtos = customer.stream().map(customerMapper::toDto).toList();
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }
    @GetMapping("/{email}")
    public ResponseEntity<List<CustomerDto>> getCustomerByEmail(@PathVariable String email) {
        List<Customer> customer = customerRepository.findByEmail(email);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CustomerDto> customerDtos = customer.stream().map(customerMapper::toDto).toList();
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<List<CustomerDto>> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        List<Customer> customer = customerRepository.findByPhoneNumber(phoneNumber);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CustomerDto> customerDtos = customer.stream().map(customerMapper::toDto).toList();
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }
    @GetMapping("/{address_id}")
    public ResponseEntity<List<CustomerDto>> getCustomerByAddress(@PathVariable int address_id) {
        List<Customer> customer = customerRepository.findByAddress(address_id);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CustomerDto> customerDtos = customer.stream().map(customerMapper::toDto).toList();
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }
}
