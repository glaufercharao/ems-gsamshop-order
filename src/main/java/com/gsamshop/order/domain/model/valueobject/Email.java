package com.gsamshop.order.domain.model.valueobject;

import com.gsamshop.order.domain.model.validator.FieldValidations;

import static com.gsamshop.order.domain.model.exception.ErrorMessages.INVALIDATION_ERROR_EMAIL_IS_INVALID;

public record Email(String value) {
    public Email(String value) {
        FieldValidations.requiresValidEmail(value, INVALIDATION_ERROR_EMAIL_IS_INVALID);
        this.value = value;
    }

    @Override
    public String toString() {
        return  value.toString();
    }
}
