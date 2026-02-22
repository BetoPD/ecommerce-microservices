package com.store.app.ProductService.service;


import com.store.app.ProductService.mapper.ProductMapper;
import com.store.app.ProductService.model.ProductEntity;
import com.store.app.ProductService.repository.ProductRepository;
import com.store.app.api.core.product.CreateProductDTO;
import com.store.app.api.core.product.IProductService;
import com.store.app.api.core.product.Product;
import com.store.app.api.core.product.UpdateProductDTO;
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
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private ServiceUtil serviceUtil;

    @Autowired
    @Qualifier("jdbcScheduler")
    private Scheduler jdbcScheduler;

    @Override
    public Mono<Product> getProductById(Long productId) {
        return Mono.fromCallable(() -> {
            ProductEntity productEntity = productRepository.findByProductId(productId).orElseThrow(() -> new NotFoundException("Invalid Product Id: " + productId));
            Product product = mapper.entityToApi(productEntity);
            product.setServiceAddress(serviceUtil.getServiceAddress());
            return product;
        }).subscribeOn(jdbcScheduler);
    }

    @Override
    public Flux<Product> getAllProducts() {
        return Mono.fromCallable(() -> {
            List<ProductEntity> productEntities = productRepository.findAll();

            List<Product> products = mapper.entityListToApiList(productEntities);

            products.forEach(product -> product.setServiceAddress(serviceUtil.getServiceAddress()));

            return products;
        }).flatMapMany(Flux::fromIterable).subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Product> createProduct(CreateProductDTO product) {
        return Mono.fromCallable(() -> {
            ProductEntity productEntity = new ProductEntity();

            productEntity.setProductId(product.getProductId());
            productEntity.setName(product.getName());
            productEntity.setDescription(product.getDescription());
            productEntity.setPrice(product.getPrice());
            productEntity.setQuantity(product.getQuantity());

            ProductEntity p = productRepository.save(productEntity);
            Product createdProduct = mapper.entityToApi(p);
            createdProduct.setServiceAddress(serviceUtil.getServiceAddress());
            return createdProduct;

        }).subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Product> updateProduct(Long productId, UpdateProductDTO product) {
        return Mono.fromCallable(() -> {
            ProductEntity productEntity = productRepository.findByProductId(productId).orElseThrow(() -> new NotFoundException("Invalid Product Id: " + productId));

            Product updateProduct = new Product();
            updateProduct.setName(product.getName());
            updateProduct.setDescription(product.getDescription());
            updateProduct.setPrice(product.getPrice());
            updateProduct.setQuantity(product.getQuantity());

            mapper.updateEntityFromApi(updateProduct, productEntity);

            ProductEntity updatedEntity = productRepository.save(productEntity);

            return mapper.entityToApi(updatedEntity);

        }).subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Void> deleteProductById(Long productId) {
        return Mono.fromRunnable(() -> {
            productRepository.delete(productRepository.findByProductId(productId).orElseThrow(() -> new NotFoundException("Invalid Product Id: " + productId)));
        }).subscribeOn(jdbcScheduler).then();
    }
}
