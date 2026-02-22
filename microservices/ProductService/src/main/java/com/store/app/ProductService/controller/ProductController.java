package com.store.app.ProductService.controller;

import com.store.app.ProductService.service.ProductService;
import com.store.app.api.core.product.CreateProductDTO;
import com.store.app.api.core.product.Product;
import com.store.app.api.core.product.UpdateProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public Mono<Product> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        return productService.createProduct(createProductDTO);
    }

    @PutMapping("/{productId}")
    public Mono<Product> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductDTO updateProductDTO) {
        return productService.updateProduct(productId, updateProductDTO);
    }

    @DeleteMapping("/{productId}")
    public Mono<Void> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProductById(productId);
    }


}
