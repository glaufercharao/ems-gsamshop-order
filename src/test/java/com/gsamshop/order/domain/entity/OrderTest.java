package com.gsamshop.order.domain.entity;


import com.gsamshop.order.domain.valueobject.Money;
import com.gsamshop.order.domain.valueobject.ProductName;
import com.gsamshop.order.domain.valueobject.Quantity;
import com.gsamshop.order.domain.valueobject.id.CustomerId;
import com.gsamshop.order.domain.valueobject.id.ProductId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;

class OrderTest {

    @Test
    public void shouldGenerate() {
        Order order = Order.draft(new CustomerId());
    }

    @Test
    public void shouldAddItem() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();

        order.addItem(
                productId,
                new ProductName("Mouse pad"),
                new Money("100"),
                new Quantity(1)
        );

        assertThat(order.items().size()).isEqualTo(1);

        OrderItem orderItem = order.items().iterator().next();

        assertWith(orderItem,
                (i) -> assertThat(i.id()).isNotNull(),
                (i) -> assertThat(i.productName()).isEqualTo(new ProductName("Mouse pad")),
                (i) -> assertThat(i.productId()).isEqualTo(productId),
                (i) -> assertThat(i.price()).isEqualTo(new Money("100")),
                (i) -> assertThat(i.quantity()).isEqualTo(new Quantity(1))
        );
    }

}