package com.store.app.ReviewService.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store.app.ReviewService.service.ReviewService;
import com.store.app.api.core.review.CreateReviewDTO;
import com.store.app.api.event.Event;
import com.store.app.api.exceptions.EventProcessingException;

@Configuration
public class MessageProcessorConfig {
    @Autowired
    private ReviewService reviewService;

    @Bean
    public Consumer<Event<Long, Object>> messageProcessor() {
        return event -> {
            switch (event.getEventType()) {
                case CREATE:
                    CreateReviewDTO createReviewDTO = (CreateReviewDTO) event.getData();
                    reviewService.createReview(createReviewDTO);
                    break;
                case DELETE:
                    reviewService.deleteReview(event.getKey());
                    break;
                default:
                    String errorMessage = "Incorrect event type: " + event.getEventType()
                            + ", expected a CREATE or DELETE event";
                    throw new EventProcessingException(errorMessage);
            }
        };
    }
}
