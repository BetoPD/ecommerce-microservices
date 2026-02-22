package com.store.app.api.core.product;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
}
