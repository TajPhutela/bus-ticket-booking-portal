package com.team4.backend.repository;

import com.team4.backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address> findByCity(String city);

    List<Address> findByZipCode(String zipCode);

    List<Address> findByState(String phoneNumber);
}
