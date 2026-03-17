package com.gsamshop.order.domain.entity;

import com.gsamshop.order.domain.valueobject.Money;
import com.gsamshop.order.domain.valueobject.ProductName;
import com.gsamshop.order.domain.valueobject.Quantity;
import com.gsamshop.order.domain.valueobject.id.OrderId;
import com.gsamshop.order.domain.valueobject.id.ProductId;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    public void shouldGenerate() {
        OrderItem.brandNew()
                .productId(new ProductId())
                .quantity(new Quantity(1))
                .orderId(new OrderId())
                .productName(new ProductName("Mouse pad"))
                .price(new Money("100"))
                .build();
    }

}