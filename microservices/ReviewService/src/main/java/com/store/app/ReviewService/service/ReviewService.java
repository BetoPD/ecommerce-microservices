package com.store.app.ReviewService.service;

import com.store.app.ReviewService.mapper.ReviewMapper;
import com.store.app.ReviewService.model.ReviewEntity;
import com.store.app.ReviewService.repository.ReviewRepository;
import com.store.app.api.core.review.CreateReviewDTO;
import com.store.app.api.core.review.IReviewService;
import com.store.app.api.core.review.Review;
import com.store.app.api.exceptions.NotFoundException;
import com.store.app.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.List;

@Service
public class ReviewService implements IReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper mapper;

    @Autowired
    private ServiceUtil serviceUtil;

    @Autowired
    @Qualifier("jdbcScheduler")
    private Scheduler jdbcScheduler;

    @Override
    public Flux<Review> getReviewsByProductId(Long productId) {
        return Mono.fromCallable(() -> {
            List<ReviewEntity> reviewsEntity = reviewRepository.findByProductId(productId);

            List<Review> reviews = mapper.entityListToApiList(reviewsEntity);

            reviews.forEach(review -> review.setServiceAddress(serviceUtil.getServiceAddress()));

            return reviews;

        }).flatMapMany(Flux::fromIterable).subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Review> createReview(CreateReviewDTO createReview) {
        return Mono.fromCallable(() -> {
            ReviewEntity reviewEntity = new ReviewEntity();

            reviewEntity.setReviewId(createReview.getReviewId());
            reviewEntity.setProductId(createReview.getProductId());
            reviewEntity.setCustomerId(createReview.getCustomerId());
            reviewEntity.setComment(createReview.getComment());

            ReviewEntity savedReviewEntity = reviewRepository.save(reviewEntity);
            Review review = mapper.entityToApi(savedReviewEntity);
            review.setServiceAddress(serviceUtil.getServiceAddress());

            return review;

        }).subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Void> deleteReview(Long reviewId) {
         return Mono.fromRunnable(() -> {
            reviewRepository.delete(reviewRepository.findByReviewId(reviewId).orElseThrow(() -> new NotFoundException("Invalid Review Id: " + reviewId)));
        }).subscribeOn(jdbcScheduler).then();
    }
}
