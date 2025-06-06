package com.team4.backend.controller;

import com.team4.backend.dto.CustomerDto;
import com.team4.backend.dto.ReviewDto;
import com.team4.backend.entities.Customer;
import com.team4.backend.entities.Review;
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
}
