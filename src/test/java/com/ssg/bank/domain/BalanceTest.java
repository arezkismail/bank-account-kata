package com.ssg.bank.domain;

import com.ssg.bank.exception.NegativeInitialBalanceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BalanceTest {

    @Test
    void create_balance_with_negative_value_should_throw_negativeInitialBalanceException() {
        //Given
        BigDecimal negativeValue = BigDecimal.TEN.negate();

        //When
        NegativeInitialBalanceException negativeInitialBalanceException = assertThrows(NegativeInitialBalanceException.class, () -> {
            new Balance(negativeValue);
        });

        //Then
        assertThat(negativeInitialBalanceException.getMessage()).isEqualTo("the balance must not be negative");
    }

}
