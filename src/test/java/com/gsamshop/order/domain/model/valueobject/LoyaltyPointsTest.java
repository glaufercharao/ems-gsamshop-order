package com.gsamshop.order.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LoyaltyPointsTest {

    @Test
    void shouldGenerate(){
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        Assertions.assertThat(loyaltyPoints.point()).isEqualTo(10);
    }

    @Test
    void shouldAddValue(){
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        var loyaltyPointsUpdated =  loyaltyPoints.add(5);
        Assertions.assertThat(loyaltyPointsUpdated.point()).isEqualTo(15);
    }
    @Test
    void shouldnotAddValue(){
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(()-> loyaltyPoints.add(-5));
        Assertions.assertThat(loyaltyPoints.point()).isEqualTo(10);
    }

    @Test
    void shouldNotAddZeroValue(){
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> loyaltyPoints.add(0));
        Assertions.assertThat(loyaltyPoints.point()).isEqualTo(10);
    }
}