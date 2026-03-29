package com.gsamshop.order.domain.model.valueobject.id;

import com.gsamshop.order.domain.model.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) {
    public CustomerId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    public CustomerId(UUID value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
