package com.gsamshop.order.infrastructure.persistence.repository;

import com.gsamshop.order.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPersistenceRepository extends JpaRepository<OrderPersistenceEntity, Long> {
}
