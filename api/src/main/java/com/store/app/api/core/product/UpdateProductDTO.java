package com.store.app.api.core.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
}
