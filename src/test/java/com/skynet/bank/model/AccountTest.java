package com.skynet.bank.model;

import com.skynet.bank.domain.exception.InsufficientAmountException;
import com.skynet.bank.domain.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.skynet.bank.domain.enums.OperationTypeEnum.DEPOSIT;
import static com.skynet.bank.domain.enums.OperationTypeEnum.WITHDRAWAL;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    private Account account;
    private static final UUID ACCOUNT_CODE = randomUUID();

    @BeforeEach
    void init() {
        this.account = new Account(ACCOUNT_CODE, now(), 20);
    }

    @Test
    void addAmount_withAmountLessThanZero_shouldThrowInsufficientAmountException() {
        //Given
        double amount = -10;

        //When
        InsufficientAmountException exception = assertThrows(InsufficientAmountException.class, () -> {
            this.account.addAmount(amount);
        });

        //Then
        assertThat(exception.getMessage()).isEqualTo("This amount must be greater than 0 !");
    }

    @Test
    void addAmount_withOneAmountBetterThanZero_shouldCreateOneOperation() {
        //Given
        double amount = 10;
        double currentBalance = this.account.getBalance();

        //When
        this.account.addAmount(amount);

        //Then
        assertThat(this.account.getBalance()).isEqualTo(currentBalance + amount);
        assertThat(this.account.getOperations()).hasSize(1);
        assertThat(this.account.getOperations()).allMatch(op -> DEPOSIT.equals(op.getOperationTypeEnum()));
    }

    @Test
    void addAmount_withTwoAmountBetterThanZero_shouldAddBalanceAndCreateTwoOperations() {
        //Given
        double amount1 = 10;
        double amount2 = 30;
        double currentBalance = this.account.getBalance();

        //When
        this.account.addAmount(amount1);
        this.account.addAmount(amount2);

        //Then
        assertThat(this.account.getBalance()).isEqualTo(currentBalance + amount1 + amount2);
        assertThat(this.account.getOperations()).hasSize(2);
        assertThat(this.account.getOperations()).allMatch(op -> DEPOSIT.equals(op.getOperationTypeEnum()));
    }

    @Test
    void addWithdrawal_withAmountLessThanZero_shouldThrowInsufficientAmountException() {
        //Given
        double amount = -10;

        //When
        InsufficientAmountException exception = assertThrows(InsufficientAmountException.class, () -> {
            this.account.addWithdrawal(amount);
        });

        //Then
        assertThat(exception.getMessage()).isEqualTo("This amount must be greater than 0 !");
    }

    @Test
    void addWithdrawal_withAmountLessThanBalance_shouldThrowRuntimeException() {
        //Given
        double amount = 30;

        //When
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            this.account.addWithdrawal(amount);
        });

        //Then
        assertThat(runtimeException.getMessage()).isEqualTo("You do not have enough money left to complete this operation !");
    }

    @Test
    void addWithdrawal_withBalanceLessThanAmount_shouldDeductFiveAndCreateOperation() {
        //Given
        double amount = 5;

        //When
        this.account.addWithdrawal(amount);

        //Then
        assertThat(this.account.getBalance()).isEqualTo(15);
        assertThat(this.account.getOperations()).hasSize(1);
        assertThat(this.account.getOperations()).allMatch(op -> WITHDRAWAL.equals(op.getOperationTypeEnum()));
    }

}
