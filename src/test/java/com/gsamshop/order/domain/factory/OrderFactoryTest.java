package com.gsamshop.order.domain.factory;

import com.gsamshop.order.domain.entity.Order;
import com.gsamshop.order.domain.entity.OrderTestDataBuilder;
import com.gsamshop.order.domain.entity.PaymentMethod;
import com.gsamshop.order.domain.entity.ProductTestDataBuilder;
import com.gsamshop.order.domain.valueobject.Billing;
import com.gsamshop.order.domain.valueobject.Product;
import com.gsamshop.order.domain.valueobject.Quantity;
import com.gsamshop.order.domain.valueobject.Shipping;
import com.gsamshop.order.domain.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertWith;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class OrderFactoryTest {

    @Test
    public void shouldGenerateFilledOrderThatCanBePlaced() {
        Shipping shipping = OrderTestDataBuilder.aShipping();
        Billing billing = OrderTestDataBuilder.aBilling();

        Product product = ProductTestDataBuilder.aProduct().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;

        Quantity quantity = new Quantity(1);
        CustomerId customerId = new CustomerId();

        Order order = OrderFactory.filled(
                customerId, shipping, billing, paymentMethod, product, quantity
        );

        assertWith(order,
                o-> assertThat(o.shipping()).isEqualTo(shipping),
                o-> assertThat(o.billing()).isEqualTo(billing),
                o-> assertThat(o.paymentMethod()).isEqualTo(paymentMethod),
                o-> Assertions.assertThat(o.items()).isNotEmpty(),
                o-> Assertions.assertThat(o.customerId()).isNotNull(),
                o-> Assertions.assertThat(o.isDraft()).isTrue()
        );

        order.place();

        assertThat(order.isPlaced()).isTrue();

    }

}