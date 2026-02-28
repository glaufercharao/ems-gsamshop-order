package com.gsamshop.order.domain.entity;

import com.gsamshop.order.domain.exception.CustomerArchivedException;
import com.gsamshop.order.domain.utility.IdGenerator;
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
            c->  assertThat(customer.birthDate()).isNull(),
            c->assertThat(customer.isPromotionNotificationAllowed()).isFalse());
    }

    @Test
    void given_archivedCustomer_whenTryToUpdateArchive_shouldGenerateException(){

        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "Anonymous",
                null,
                "janonymous@anonymous.com",
                "000-000-000",
                "000-00-000",
                false,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                10);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(()-> customer.changeName("Antonio"));

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeEmail("email@gmail.com"));

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(()-> customer.changePhone("200-100-000"));
    }


    @Test
    void given_bradNewCustomer_whenAddLoyaltypoints_shouldSumPoints(){

        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1991, 7, 5),
                "john.doe@gmail.com",
                "478-256-2504",
                "280-008-908",
                true,
                OffsetDateTime.now());

        customer.addLoyaltyPoints(10);
        customer.addLoyaltyPoints(20);

        assertThat(customer.loyaltyPoints()).isEqualTo(30);

    }

    @Test
    void given_brandNewCustomer_whenAddinvalidLoyaltyPoints_shouldGenerateException() {

        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "Anonymous",
                null,
                "janonymous@anonymous.com",
                "000-000-000",
                "000-00-000",
                false,
                OffsetDateTime.now());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(0));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(-10));
    }
}