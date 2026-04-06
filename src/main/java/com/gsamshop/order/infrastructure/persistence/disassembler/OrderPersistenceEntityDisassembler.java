package com.gsamshop.order.infrastructure.persistence.disassembler;

import com.gsamshop.order.domain.model.entity.Order;
import com.gsamshop.order.domain.model.entity.OrderStatus;
import com.gsamshop.order.domain.model.entity.PaymentMethod;
import com.gsamshop.order.domain.model.valueobject.Money;
import com.gsamshop.order.domain.model.valueobject.Quantity;
import com.gsamshop.order.domain.model.valueobject.id.CustomerId;
import com.gsamshop.order.domain.model.valueobject.id.OrderId;
import com.gsamshop.order.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class OrderPersistenceEntityDisassembler {

    public Order toDomainEntity(OrderPersistenceEntity persistenceEntity) {
        return Order.existing()
                .id(new OrderId(persistenceEntity.getId()))
                .customerId(new CustomerId(persistenceEntity.getCustomerId()))
                .totalAmount(new Money(persistenceEntity.getTotalAmount()))
                .totalItems(new Quantity(persistenceEntity.getTotalItems()))
                .status(OrderStatus.valueOf(persistenceEntity.getStatus()))
                .paymentMethod(PaymentMethod.valueOf(persistenceEntity.getPaymentMethod()))
                .placedAt(persistenceEntity.getPlacedAt())
                .paidAt(persistenceEntity.getPaidAt())
                .canceledAt(persistenceEntity.getCanceledAt())
                .readyAt(persistenceEntity.getReadyAt())
                .items(new HashSet<>())
                .build();
    }

}