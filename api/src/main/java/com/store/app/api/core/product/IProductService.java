package com.store.app.api.core.product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    Mono<Product> getProductById(Long productId);
    Flux<Product> getAllProducts();
    Mono<Product> createProduct(CreateProductDTO product);
    Mono<Product> updateProduct(Long productId, UpdateProductDTO product);
    Mono<Void> deleteProductById(Long productId);
}
