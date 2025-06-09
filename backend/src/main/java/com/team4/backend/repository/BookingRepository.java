package com.team4.backend.repository;

import com.team4.backend.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByTrip_Id(Integer tripId);
    List<Booking> findByStatusIgnoreCase(String status);
    List<Booking> findBySeatNumber(Integer seatNumber);


}