package com.store.app.api.core.review;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IReviewService {
    Flux<Review> getReviewsByProductId(Long productId);
    Mono<Review> createReview(CreateReviewDTO createReview);
    Mono<Void> deleteReview(Long reviewId);
}
