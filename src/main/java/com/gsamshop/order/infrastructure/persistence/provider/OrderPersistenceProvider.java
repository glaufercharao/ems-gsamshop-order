package com.gsamshop.order.infrastructure.persistence.provider;

import com.gsamshop.order.domain.model.entity.Order;
import com.gsamshop.order.domain.model.repository.Orders;
import com.gsamshop.order.domain.model.valueobject.id.OrderId;
import com.gsamshop.order.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.gsamshop.order.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.gsamshop.order.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.gsamshop.order.infrastructure.persistence.repository.OrderPersistenceRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderPersistenceProvider implements Orders {

    private final OrderPersistenceRepository orderRepository;
    private final OrderPersistenceEntityAssembler assembler;
    private final OrderPersistenceEntityDisassembler disassembler;

    private final EntityManager entityManager;

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
        long orderId = aggregateRoot.id().value().toLong();

        orderRepository.findById(orderId)
                .ifPresentOrElse(
                        (persistenceEntity) -> {
                            update(aggregateRoot, persistenceEntity);
                        },
                        ()-> {
                            insert(aggregateRoot);
                        }
                );
    }

    private void update(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {
        persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
        entityManager.detach(persistenceEntity);
        persistenceEntity = orderRepository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
    }

    private void insert(Order aggregateRoot) {
        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
        orderRepository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
    }

    @SneakyThrows
    private void updateVersion(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {
        Field version = aggregateRoot.getClass().getDeclaredField("version");
        version.setAccessible(true);
        ReflectionUtils.setField(version, aggregateRoot, persistenceEntity.getVersion());
        version.setAccessible(false);
    }

    @Override
    public int count() {
        return 0;
    }
}
