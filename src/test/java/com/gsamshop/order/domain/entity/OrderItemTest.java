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
                .product(ProductTestDataBuilder.aProduct().build())
                .quantity(new Quantity(1))
                .orderId(new OrderId())
                .build();
    }

}