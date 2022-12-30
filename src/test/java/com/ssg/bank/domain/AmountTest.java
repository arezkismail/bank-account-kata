package com.ssg.bank.domain;

import com.ssg.bank.exception.InsufficientAmountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AmountTest {

    @Test
    void create_amount_with_negative_value_should_throw_insufficientAmountException() {
        //Given
        BigDecimal negativeValue = BigDecimal.TEN.negate();

        //When
        InsufficientAmountException negativeAmoutException = assertThrows(InsufficientAmountException.class, () -> {
            new Amount(negativeValue);
        });

        //Then
        assertThat(negativeAmoutException.getMessage()).isEqualTo("Amount should not be null or negative");
    }

    @Test
    void create_zero_amount_with_account_of_zero_balance_should_throw_insufficientAmountException() {
        //Given
        BigDecimal nullValue = BigDecimal.ZERO;

        //When
        InsufficientAmountException insufficientAmountException = assertThrows(InsufficientAmountException.class, () -> {
            new Amount(nullValue);
        });

        assertThat(insufficientAmountException.getMessage()).isEqualTo("Amount should not be null or negative");
    }
}
