package com.store.app.api.core.review;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Long reviewId;
    private Long productId;
    private Long customerId;
    private String comment;
    private String serviceAddress;
    private LocalDateTime createdAt;
}
