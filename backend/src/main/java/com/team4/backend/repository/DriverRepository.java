package com.team4.backend.repository;


import com.team4.backend.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    List<Driver> findByName(String name);

    List<Driver> findByAddressId(int addressId);

    List<Driver> findByOfficeId(int officeId);
}
