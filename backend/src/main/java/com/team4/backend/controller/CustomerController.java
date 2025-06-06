package com.team4.backend.controller;

import com.team4.backend.entities.Customer;
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

    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customer = customerRepository.findAll();
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/{customer_id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customer_id") int customer_id) {
        Optional<Customer> customer = customerRepository.findById(customer_id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
