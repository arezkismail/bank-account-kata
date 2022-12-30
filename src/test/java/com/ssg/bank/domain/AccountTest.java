package com.ssg.bank.domain;

import com.ssg.bank.exception.BalanceNotSufficientException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    private static final LocalDateTime NOW = now();

    @Test
    void create_account_with_zero_balance() {
        //Given
        Balance balance = new Balance(BigDecimal.ZERO);
        //When
        Account account = new Account(balance, NOW);
        //Then
        assertThat(account.getBalance().balance()).isZero();
        assertThat(account.getCreationDate()).isEqualTo(NOW);
    }

    @Test
    void create_account_with_ten_balance() {
        //Given
        Balance balance = new Balance(BigDecimal.TEN);
        //When
        Account account = new Account(balance, NOW);
        //Then
        assertThat(account.getBalance()).isEqualTo(balance);
        assertThat(account.getCreationDate()).isEqualTo(NOW);
    }

    @Test
    void deposit_ten_amount_with_account_of_zero_balance() {
        //Given
        Amount amount = new Amount(BigDecimal.TEN);
        Balance balance = new Balance(BigDecimal.ZERO);
        Account account = new Account(balance, NOW);

        //When
        account.deposit(amount);

        //Then
        assertThat(account.getBalance().balance()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    void withdrawal_ten_amount_with_account_of_ten_balance() {
        //Given
        Balance  balance = new Balance(BigDecimal.TEN);
        Account account = new Account(balance, NOW);
        Amount amount = new Amount(BigDecimal.TEN);

        //When
        account.withdrawal(amount);

        //Then
        assertThat(account.getBalance().balance()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void withdrawal_ten_amount_with_account_of_zero_balance_should_throw_balanceNotSufficientException() {
        //Given
        Balance  balance = new Balance(BigDecimal.ZERO);
        Account account = new Account(balance, NOW);
        Amount amount = new Amount(BigDecimal.TEN);

        //When
        BalanceNotSufficientException balanceNotSufficientException = assertThrows(BalanceNotSufficientException.class, () -> {
            account.withdrawal(amount);
        });


        //Then
        assertThat(balanceNotSufficientException.getMessage()).isEqualTo("balance not sufficient");
    }


}
