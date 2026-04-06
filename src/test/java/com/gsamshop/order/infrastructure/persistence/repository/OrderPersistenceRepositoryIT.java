package com.gsamshop.order.infrastructure.persistence.repository;

import com.gsamshop.order.domain.model.utility.IdGenerator;
import com.gsamshop.order.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.gsamshop.order.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.gsamshop.order.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(SpringDataAuditingConfig.class)
class OrderPersistenceRepositoryIT {

    private final OrderPersistenceRepository orderPersistenceRepository;

    @Autowired
    public OrderPersistenceRepositoryIT(OrderPersistenceRepository orderPersistenceRepository) {
        this.orderPersistenceRepository = orderPersistenceRepository;
    }

    @Test
    public void shouldPersist(){
        long orderId = IdGenerator.gererateTSID().toLong();

        OrderPersistenceEntity entity = OrderPersistenceEntity.builder()
                .id(orderId)
                .customerId(IdGenerator.generateTimeBasedUUID())
                .totalItems(2)
                .totalAmount(new BigDecimal(1000))
                .status("DRAFT")
                .paymentMethod("CREDIT_CARD")
                .placedAt(OffsetDateTime.now())
                .build();

        orderPersistenceRepository.saveAndFlush(entity);
        Assertions.assertThat(orderPersistenceRepository.existsById(orderId)).isTrue();
    }

    @Test
    public void shouldCount(){
        long orderCount = orderPersistenceRepository.count();
        Assertions.assertThat(orderCount).isZero();
    }

    @Test
    public void shouldSetAuditingValues() {
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();
        entity = orderPersistenceRepository.saveAndFlush(entity);

        Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();

        Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }

}