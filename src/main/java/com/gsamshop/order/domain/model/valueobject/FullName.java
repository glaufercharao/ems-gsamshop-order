package com.gsamshop.order.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record FullName(String firstName, String lastName) {

    public FullName(String firstName, String lastName) {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);

        if(firstName.isBlank()){
            throw new IllegalArgumentException();
        }
        if(lastName.isBlank()){
            throw new IllegalArgumentException();
        }

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    @Override
    public String toString() {
        return  firstName +" "+ lastName;
    }
}
