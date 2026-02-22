package com.store.app.ReviewService.controller;

import com.store.app.ReviewService.service.ReviewService;
import com.store.app.api.core.review.CreateReviewDTO;
import com.store.app.api.core.review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{productId}")
    public Flux<Review> getReviewsByProductId(@PathVariable Long productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    @PostMapping
    public Mono<Review> createReview(@RequestBody CreateReviewDTO createReviewDTO) {
        return reviewService.createReview(createReviewDTO);
    }

    @DeleteMapping("/{reviewId}")
    public Mono<Void> deleteReviewById(@PathVariable Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }
}
