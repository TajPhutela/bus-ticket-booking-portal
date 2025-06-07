package com.team4.backend.repository;

import com.team4.backend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findByCustomerId(Integer customerId);

    List<Review> findByTripId(Integer tripId);

    List<Review> findByRating(Integer Rating);

    List<Review> findByReviewDate(Instant reviewDate);

    List<Review> findByCustomerIdAndTripId(Integer customerId, Integer tripId);

    List<Review> findByRatingGreaterThan(Integer rating);

    List<Review> findByRatingLessThan(Integer rating);
}
