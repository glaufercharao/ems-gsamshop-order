package com.gsamshop.order.infrastructure.persistence.provider;

import com.gsamshop.order.domain.model.entity.Order;
import com.gsamshop.order.domain.model.entity.OrderStatus;
import com.gsamshop.order.domain.model.entity.OrderTestDataBuilder;
import com.gsamshop.order.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.gsamshop.order.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.gsamshop.order.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.gsamshop.order.infrastructure.persistence.repository.OrderPersistenceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({
        OrderPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class
})
class OrdersPersistenceProviderIT {

    private OrderPersistenceProvider persistenceProvider;
    private OrderPersistenceRepository entityRepository;

    @Autowired
    public OrdersPersistenceProviderIT(OrderPersistenceProvider persistenceProvider, OrderPersistenceRepository entityRepository) {
        this.persistenceProvider = persistenceProvider;
        this.entityRepository = entityRepository;
    }

    @Test
    public void shouldUpdateAndKeepPersistenceEntityState() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        long orderId = order.id().value().toLong();
        persistenceProvider.add(order);

        var persistenceEntity = entityRepository.findById(orderId).orElseThrow();

        Assertions.assertThat(persistenceEntity.getStatus()).isEqualTo(OrderStatus.PLACED.name());

        Assertions.assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedByUserId()).isNotNull();

        order = persistenceProvider.ofId(order.id()).orElseThrow();
        order.markAsPaid();
        persistenceProvider.add(order);

        persistenceEntity = entityRepository.findById(orderId).orElseThrow();

        Assertions.assertThat(persistenceEntity.getStatus()).isEqualTo(OrderStatus.PAID.name());

        Assertions.assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedByUserId()).isNotNull();

    }
}