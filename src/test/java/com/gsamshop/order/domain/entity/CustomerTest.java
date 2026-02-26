package com.gsamshop.order.domain.entity;

import com.gsamshop.order.domain.utility.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.*;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException(){

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1991,7,5),
                "invalid",
                "478-256-2504",
                "280-008-908",
                true,
                OffsetDateTime.now()));

    }

    @Test
    void given_invalidEmail_whenTryUpdatedCustomerEmail_shouldGenerateException(){

        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1991, 7, 5),
                "john.doe@gmail.com",
                "478-256-2504",
                "280-008-908",
                true,
                OffsetDateTime.now());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()->{
                    customer.changeEmail("invalid");
                });

    }
    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize(){

        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1991, 7, 5),
                "john.doe@gmail.com",
                "478-256-2504",
                "280-008-908",
                true,
                OffsetDateTime.now());

        customer.archive();
        assertWith(customer,
            c-> assertThat(c.fullName()).isEqualTo("Anonymous"),
            c-> assertThat(c.phone()).isEqualTo("000-000-000"),
            c-> assertThat(c.document()).isEqualTo("000-00-000"),
            c -> assertThat(c.email()).isNotEqualTo("john.doe@gmail.com"),
            c->  assertThat(customer.birthDate()).isNull());
    }

}