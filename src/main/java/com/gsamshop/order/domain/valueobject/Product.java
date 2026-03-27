package com.gsamshop.order.domain.valueobject;

import com.gsamshop.order.domain.valueobject.id.ProductId;
import lombok.Builder;

import java.util.Objects;

@Builder
public record Product(
        ProductId id,
        ProductName name,
        Money price,
        Boolean inStock
) {
    public Product {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);
        Objects.requireNonNull(inStock);
    }
}
