package com.gsamshop.order.infrastructure.persistence.disassembler;

import com.gsamshop.order.domain.model.entity.Order;
import com.gsamshop.order.domain.model.entity.OrderStatus;
import com.gsamshop.order.domain.model.entity.PaymentMethod;
import com.gsamshop.order.domain.model.valueobject.*;
import com.gsamshop.order.domain.model.valueobject.id.CustomerId;
import com.gsamshop.order.domain.model.valueobject.id.OrderId;
import com.gsamshop.order.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.gsamshop.order.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.gsamshop.order.infrastructure.persistence.embeddable.RecipientEmbeddable;
import com.gsamshop.order.infrastructure.persistence.embeddable.ShippingEmbeddable;
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
                .version(persistenceEntity.getVersion())
                .build();
    }

    private Shipping toShippingValueObject(ShippingEmbeddable shippingEmbeddable) {
        RecipientEmbeddable recipientEmbeddable = shippingEmbeddable.getRecipient();
        return Shipping.builder()
                .cost(new Money(shippingEmbeddable.getCost()))
                .expectedDate(shippingEmbeddable.getExpectedDate())
                .recipient(
                        Recipient.builder()
                                .fullName(new FullName(recipientEmbeddable.getFirstName(), recipientEmbeddable.getLastName()))
                                .document(new Document(recipientEmbeddable.getDocument()))
                                .phone(new Phone(recipientEmbeddable.getPhone()))
                                .build()
                )
                .address(toAddressValueObject(shippingEmbeddable.getAddress()))
                .build();
    }

    private Billing toBillingValueObject(BillingEmbeddable billingEmbeddable) {
        return Billing.builder()
                .fullName(new FullName(billingEmbeddable.getFirstName(), billingEmbeddable.getLastName()))
                .document(new Document(billingEmbeddable.getDocument()))
                .phone(new Phone(billingEmbeddable.getPhone()))
                .address(toAddressValueObject(billingEmbeddable.getAddress()))
                .build();
    }

    private Address toAddressValueObject(AddressEmbeddable address) {
        return Address.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(new ZipCode(address.getZipCode()))
                .build();
    }

}