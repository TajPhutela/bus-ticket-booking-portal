package com.team4.backend.controller;

import com.team4.backend.dto.ReviewDto;
import com.team4.backend.entities.Review;
import com.team4.backend.mapper.ReviewMapper;
import com.team4.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    public ResponseEntity<List<ReviewDto>> getAllReview(){
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> reviewMapper.toDto(review))
                .toList();
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable("review_id") int review_id){
        Optional<Review> review = reviewRepository.findById(review_id);
        if(review.isPresent()){
            ReviewDto reviewDto = reviewMapper.toDto(review.get());
            return new ResponseEntity<>(reviewDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}