package com.team4.backend.controller;

import com.team4.backend.dto.ReviewDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.entities.Review;
import com.team4.backend.mapper.ReviewMapper;
import com.team4.backend.repository.ReviewRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getAllReviews() {
        List<ReviewDto> reviewDtos = reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(reviewDtos));
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<ApiResponse<ReviewDto>> getReviewById(@PathVariable("review_id") int reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success(reviewMapper.toDto(review.get())));
        }
        return new ResponseEntity<>(
                ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Review with ID " + reviewId + " not found"),
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/tripid/{trip_id}")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getReviewsByTripId(@PathVariable("trip_id") Integer tripId) {
        List<ReviewDto> dtos = reviewRepository.findByTripId(tripId)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/customerid/{customer_id}")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getReviewsByCustomerId(@PathVariable("customer_id") Integer customerId) {
        List<ReviewDto> dtos = reviewRepository.findByCustomerId(customerId)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getReviewsByRating(@PathVariable("rating") Integer rating) {
        List<ReviewDto> dtos = reviewRepository.findByRating(rating)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/gt/{rating}")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getReviewsWithRatingGreaterThan(@PathVariable("rating") Integer rating) {
        List<ReviewDto> dtos = reviewRepository.findByRatingGreaterThan(rating)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/lt/{rating}")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getReviewsWithRatingLessThan(@PathVariable("rating") Integer rating) {
        List<ReviewDto> dtos = reviewRepository.findByRatingLessThan(rating)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/customer/{customer_id}/trip/{trip_id}")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getReviewsByCustomerAndTrip(
            @PathVariable("customer_id") Integer customerId,
            @PathVariable("trip_id") Integer tripId) {
        List<ReviewDto> dtos = reviewRepository.findByCustomerIdAndTripId(customerId, tripId)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/date/{reviewDate}")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getReviewsByReviewDate(@PathVariable("reviewDate") String reviewDateStr) {
        try {
            Instant reviewDate = Instant.parse(reviewDateStr);
            List<ReviewDto> dtos = reviewRepository.findByReviewDate(reviewDate)
                    .stream()
                    .map(reviewMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(dtos));
        } catch (Exception e) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Invalid date format: " + reviewDateStr),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<ReviewDto>> addReview(@Valid @RequestBody ReviewDto reviewDto) {
        Review review = reviewRepository.save(reviewMapper.toEntity(reviewDto));
        return new ResponseEntity<>(
                ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Review Created",
                        reviewMapper.toDto(review)
                ),
                HttpStatus.CREATED
        );

    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<ReviewDto>> updateReview(@Valid @RequestBody ReviewDto reviewDto) {
        Review review = reviewRepository.save(reviewMapper.toEntity(reviewDto));
        return ResponseEntity.ok(ApiResponse.success(reviewMapper.toDto(review)));
    }


}
