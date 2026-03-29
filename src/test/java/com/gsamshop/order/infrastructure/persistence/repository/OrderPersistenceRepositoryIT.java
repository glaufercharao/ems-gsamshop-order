package com.gsamshop.order.infrastructure.persistence.repository;

import com.gsamshop.order.domain.model.utility.IdGenerator;
import com.gsamshop.order.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@SpringBootTest
@Transactional
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
                .paymentMethod("CREDT_CARD")
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
}