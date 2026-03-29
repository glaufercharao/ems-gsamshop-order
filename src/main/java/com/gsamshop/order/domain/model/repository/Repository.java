package com.gsamshop.order.domain.model.repository;

import com.gsamshop.order.domain.model.entity.AggregateRoot;

import java.util.Optional;

public interface Repository<T extends AggregateRoot<ID>,ID> {
    Optional<T> ofId(ID id);
    boolean exists(ID id);
    void add(T aggegateRoot);
    int count();
}
