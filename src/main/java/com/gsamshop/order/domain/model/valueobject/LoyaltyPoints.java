package com.gsamshop.order.domain.model.valueobject;

import java.util.Objects;

public record LoyaltyPoints(Integer point) implements Comparable<LoyaltyPoints> {
    public static final LoyaltyPoints ZERO = new LoyaltyPoints(0);

    public LoyaltyPoints() {
        this(0);
    }
    public LoyaltyPoints(Integer point) {
        Objects.requireNonNull(point);
        if(point <0){
            throw new IllegalArgumentException();
        }
        this.point = point;
    }

    public LoyaltyPoints add(Integer point){
        return add(new LoyaltyPoints(point));
    }
    public LoyaltyPoints add(LoyaltyPoints loyaltyPoints){
        Objects.requireNonNull(loyaltyPoints);
        if(loyaltyPoints.point <= 0){
            throw new IllegalArgumentException();
        }

        return new LoyaltyPoints(this.point + loyaltyPoints.point());
    }

    @Override
    public String toString() {
        return  point.toString();
    }

    @Override
    public int compareTo(LoyaltyPoints o) {
        return this.point().compareTo(o.point());
    }
}
