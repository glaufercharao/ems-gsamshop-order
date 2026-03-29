package com.gsamshop.order.domain.model.valueobject;


import com.gsamshop.order.domain.model.validator.FieldValidations;

public record ProductName(String value) {

    public ProductName {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }

}