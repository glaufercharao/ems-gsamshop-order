package com.gsamshop.order.infrastructure.persistence.provider;

import com.gsamshop.order.domain.model.entity.Order;
import com.gsamshop.order.domain.model.repository.Orders;
import com.gsamshop.order.domain.model.valueobject.id.OrderId;
import com.gsamshop.order.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.gsamshop.order.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.gsamshop.order.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.gsamshop.order.infrastructure.persistence.repository.OrderPersistenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderPersistenceProvider implements Orders {

    private final OrderPersistenceRepository orderRepository;
    private final OrderPersistenceEntityAssembler assembler;
    private final OrderPersistenceEntityDisassembler disassembler;

    @Override
    public Optional<Order> ofId(OrderId orderId) {
        Optional<OrderPersistenceEntity> possibleEntity = orderRepository.findById(orderId.value().toLong());
        return possibleEntity.map(disassembler::toDomainEntity);
    }

    @Override
    public boolean exists(OrderId orderId) {
        return false;
    }

    @Override
    public void add(Order aggregateRoot) {
        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
        orderRepository.saveAndFlush(persistenceEntity);
    }

    @Override
    public int count() {
        return 0;
    }
}
