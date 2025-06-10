package com.team4.backend.controller;

import com.team4.backend.dto.request.ReviewRequestDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.dto.response.ReviewResponseDto;
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
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getAllReviews() {
        List<ReviewResponseDto> dtos = reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> getReviewById(@PathVariable("review_id") int reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        return review.map(r -> ResponseEntity.ok(ApiResponse.success(reviewMapper.toResponseDto(r))))
                .orElseGet(() -> new ResponseEntity<>(
                        ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Review with ID " + reviewId + " not found"),
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/tripid/{trip_id}")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getReviewsByTripId(@PathVariable("trip_id") Integer tripId) {
        List<ReviewResponseDto> dtos = reviewRepository.findByTripId(tripId)
                .stream()
                .map(reviewMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/customerid/{customer_id}")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getReviewsByCustomerId(@PathVariable("customer_id") Integer customerId) {
        List<ReviewResponseDto> dtos = reviewRepository.findByCustomerId(customerId)
                .stream()
                .map(reviewMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getReviewsByRating(@PathVariable("rating") Integer rating) {
        List<ReviewResponseDto> dtos = reviewRepository.findByRating(rating)
                .stream()
                .map(reviewMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/gt/{rating}")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getReviewsWithRatingGreaterThan(@PathVariable("rating") Integer rating) {
        List<ReviewResponseDto> dtos = reviewRepository.findByRatingGreaterThan(rating)
                .stream()
                .map(reviewMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/lt/{rating}")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getReviewsWithRatingLessThan(@PathVariable("rating") Integer rating) {
        List<ReviewResponseDto> dtos = reviewRepository.findByRatingLessThan(rating)
                .stream()
                .map(reviewMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/customer/{customer_id}/trip/{trip_id}")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getReviewsByCustomerAndTrip(
            @PathVariable("customer_id") Integer customerId,
            @PathVariable("trip_id") Integer tripId) {
        List<ReviewResponseDto> dtos = reviewRepository.findByCustomerIdAndTripId(customerId, tripId)
                .stream()
                .map(reviewMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/date/{reviewDate}")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getReviewsByReviewDate(@PathVariable("reviewDate") String reviewDateStr) {
        try {
            Instant reviewDate = Instant.parse(reviewDateStr);
            List<ReviewResponseDto> dtos = reviewRepository.findByReviewDate(reviewDate)
                    .stream()
                    .map(reviewMapper::toResponseDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(dtos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(400, "Invalid date format: " + reviewDateStr));
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> addReview(@Valid @RequestBody ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.save(reviewMapper.toEntity(reviewRequestDto));
        return new ResponseEntity<>(
                ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Review Created",
                        reviewMapper.toResponseDto(review)
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> updateReview(
            @PathVariable Integer id,
            @Valid @RequestBody ReviewRequestDto reviewRequestDto) {

        if (!reviewRepository.existsById(id)) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Review with ID " + id + " not found"),
                    HttpStatus.NOT_FOUND
            );
        }

        Review review = reviewMapper.toEntity(reviewRequestDto);
        review.setId(id);
        Review saved = reviewRepository.save(review);

        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "Review updated successfully", reviewMapper.toResponseDto(saved))
        );
    }
}
