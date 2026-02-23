package com.store.app.ProductService.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store.app.ProductService.service.ProductService;
import com.store.app.api.core.product.CreateProductDTO;
import com.store.app.api.core.product.UpdateProductDTO;
import com.store.app.api.event.Event;
import com.store.app.api.exceptions.EventProcessingException;

@Configuration
public class MessageProcessorConfig {

    @Autowired
    private ProductService productService;

    @Bean
    public Consumer<Event<Long, Object>> messageProcessor() {
        return event -> {
            switch (event.getEventType()) {
                case CREATE:
                    CreateProductDTO createProductDTO = (CreateProductDTO) event.getData();
                    productService.createProduct(createProductDTO);
                    break;
                case UPDATE:
                    UpdateProductDTO updateProductDTO = (UpdateProductDTO) event.getData();
                    productService.updateProduct(event.getKey(), updateProductDTO);
                    break;
                case DELETE:
                    productService.deleteProductById(event.getKey());
                    break;
                default:
                    String errorMessage = "Incorrect event type: " + event.getEventType()
                            + ", expected a CREATE or DELETE event";
                    throw new EventProcessingException(errorMessage);
            }
        };
    }

}
