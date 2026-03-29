package com.gsamshop.order.domain.model.factory;

import com.gsamshop.order.domain.model.entity.Order;
import com.gsamshop.order.domain.model.entity.OrderTestDataBuilder;
import com.gsamshop.order.domain.model.entity.PaymentMethod;
import com.gsamshop.order.domain.model.entity.ProductTestDataBuilder;
import com.gsamshop.order.domain.model.valueobject.Billing;
import com.gsamshop.order.domain.model.valueobject.Product;
import com.gsamshop.order.domain.model.valueobject.Quantity;
import com.gsamshop.order.domain.model.valueobject.Shipping;
import com.gsamshop.order.domain.model.valueobject.id.CustomerId;
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