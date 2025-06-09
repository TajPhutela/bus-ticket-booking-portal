package com.team4.backend.repository;

import com.team4.backend.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {

    List<Bus> findByCapacity(int capacity);

    List<Bus> findByType(String type);

    Optional<Bus> findByRegistrationNumber(String registrationNumber);

    List<Bus> findByOfficeId(Integer officeId);
}
