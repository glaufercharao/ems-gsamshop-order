package com.gsamshop.order.domain.model.repository;

import com.gsamshop.order.domain.model.entity.AggregateRoot;

import java.util.Optional;

public interface RemoveCapableRepository<T extends AggregateRoot<ID>,ID> extends Repository<T, ID> {
    void remove(T t);
    void remove(ID id);
}
