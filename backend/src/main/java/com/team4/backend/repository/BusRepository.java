package com.team4.backend.repository;

import com.team4.backend.dto.response.BusTypeCountDto;
import com.team4.backend.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {

    List<Bus> findByCapacity(int capacity);

    List<Bus> findByTypeIgnoreCase(String type);

    Optional<Bus> findByRegistrationNumber(String registrationNumber);

    List<Bus> findByOfficeId(Integer officeId);

    // JPQL query to get count grouped by type
    @Query("SELECT new com.team4.backend.dto.response.BusTypeCountDto(b.type, COUNT(b), null) FROM Bus b GROUP BY b.type")
    List<BusTypeCountDto> getBusCountsGroupedByType();

    // get all buses of a specific type
    List<Bus> findByType(String type);
    
}
