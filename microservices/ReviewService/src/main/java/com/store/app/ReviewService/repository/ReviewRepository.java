package com.store.app.ReviewService.repository;

import com.store.app.ReviewService.model.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByCustomerId(Long customerId);
    List<ReviewEntity> findByProductId(Long productId);
    Optional<ReviewEntity> findByReviewId(Long reviewId);
}
