package com.gsamshop.order.domain.exception;

import com.gsamshop.order.domain.entity.OrderStatus;
import com.gsamshop.order.domain.valueobject.id.OrderId;

public class OrderCannotBeEditedException extends DomainException {

    public OrderCannotBeEditedException(OrderId id, OrderStatus status) {
        super(String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_EDITED, id, status));
    }
}