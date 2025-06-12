package com.team4.backend.repository;

import com.team4.backend.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByTrip_Id(Integer tripId);
    List<Booking> findByStatus(String status);
    List<Booking> findBySeatNumber(Integer seatNumber);


}