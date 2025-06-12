package com.team4.backend.repository;

import com.team4.backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByName(String name);
    List<Customer> findByEmail(String email);
    List<Customer> findByPhone(String phoneNumber);
    List<Customer> findByAddress_Id(int addressId);
}
