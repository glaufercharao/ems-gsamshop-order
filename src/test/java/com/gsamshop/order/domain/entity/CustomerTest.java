package com.gsamshop.order.domain.entity;

import com.gsamshop.order.domain.exception.CustomerArchivedException;
import com.gsamshop.order.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.*;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException(){

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new Customer(
               new CustomerId(),
                new FullName("John"," Doe"),
                new BirthDate(LocalDate.of(1991,7,5)),
                new Email("invalid"),
                new Phone("478-256-2504"),
                new Document("280-008-908"),
                true,
                OffsetDateTime.now(),
                        Address.builder()
                                .street("Bourbon Street")
                                .number("1134")
                                .neighborhood("North Ville")
                                .city("York")
                                .state("South California")
                                .zipCode(new ZipCode("12345"))
                                .complement("Apt. 114")
                                .build()));

    }

    @Test
    void given_invalidEmail_whenTryUpdatedCustomerEmail_shouldGenerateException(){

        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John"," Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("280-008-908"),
                true,
                OffsetDateTime.now(),
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()->{
                    customer.changeEmail(new Email("invalid"));
                });

    }
    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize(){

        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John"," Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("280-008-908"),
                true,
                OffsetDateTime.now(),
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build());

        customer.archive();
        assertWith(customer,
                c -> assertThat(c.fullName()).isEqualTo(new FullName("Anonymous","Anonymous")),
                c -> assertThat(c.email()).isNotEqualTo(new Email("john.doe@gmail.com")),
                c -> assertThat(c.phone()).isEqualTo(new Phone("000-000-0000")),
                c -> assertThat(c.document()).isEqualTo(new Document("000-00-0000")),
                c -> assertThat(c.birthDate()).isNull(),
                c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse(),
                c -> assertThat(c.address()).isEqualTo(
                        Address.builder()
                                .street("Bourbon Street")
                                .number("Anonymized")
                                .neighborhood("North Ville")
                                .city("York")
                                .state("South California")
                                .zipCode(new ZipCode("12345"))
                                .complement(null)
                                .build()));
    }

    @Test
    void given_archivedCustomer_whenTryToUpdateArchive_shouldGenerateException(){

        Customer customer = new Customer(
                new CustomerId(),
                new FullName("Anonymous"," Anonymous"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("janonymous@anonymous.com"),
                new Phone("000-000-000"),
                new Document("000-00-000"),
                false,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                new LoyaltyPoints(10),
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build());

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(()-> customer.changeEmail(new Email("email@gmail.com")));

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(()-> customer.changePhone(new Phone("123-123-1111")));

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::enablePromotionNotifications);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::disablePromotionNotifications);
    }


    @Test
    void given_bradNewCustomer_whenAddLoyaltypoints_shouldSumPoints(){

        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John"," Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("280-008-908"),
                true,
                OffsetDateTime.now(),
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build());

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
        customer.addLoyaltyPoints(new LoyaltyPoints(20));

        assertThat(customer.loyaltyPoints().point()).isEqualTo(30);

    }

    @Test
    void given_brandNewCustomer_whenAddinvalidLoyaltyPoints_shouldGenerateException() {

        Customer customer = new Customer(
                new CustomerId(),
                new FullName("Anonymous","Anonymous"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("janonymous@anonymous.com"),
                new Phone("000-000-000"),
                new Document("000-00-000"),
                false,
                OffsetDateTime.now(),
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(LoyaltyPoints.ZERO));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(-10)));
    }
}