package com.store.app.ProductService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
}
