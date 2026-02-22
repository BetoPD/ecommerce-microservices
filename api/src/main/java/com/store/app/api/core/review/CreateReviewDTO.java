package com.store.app.api.core.review;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewDTO {
    private Long reviewId;
    private Long productId;
    private Long customerId;
    private String comment;
}
