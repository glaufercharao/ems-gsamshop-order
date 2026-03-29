package com.gsamshop.order.domain.model.exception;

import com.gsamshop.order.domain.model.entity.OrderStatus;
import com.gsamshop.order.domain.model.valueobject.id.OrderId;

import static com.gsamshop.order.domain.model.exception.ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED;

public class OrderStatusCannotBeChangedException extends DomainException {

    public OrderStatusCannotBeChangedException(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGED,id, status, newStatus));
    }
}
