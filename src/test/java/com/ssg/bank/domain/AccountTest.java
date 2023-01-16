package com.ssg.bank.domain;

import com.ssg.bank.domain.mocks.FakeOperationPrinter;
import com.ssg.bank.exception.BalanceNotSufficientException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.LinkedList;

import static com.ssg.bank.domain.enums.OperationType.DEPOSIT;
import static com.ssg.bank.domain.enums.OperationType.WITHDRAWAL;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    private final static Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());


    @Test
    void create_account_with_zero_balance() {
        //Given
        Balance balance = new Balance(BigDecimal.ZERO);
        //When
        Account account = new Account(balance);
        //Then
        assertThat(account.getBalance()).isEqualTo(balance);
    }

    @Test
    void create_account_with_ten_balance() {
        //Given
        Balance balance = new Balance(BigDecimal.TEN);
        //When
        Account account = new Account(balance);
        //Then
        assertThat(account.getBalance()).isEqualTo(balance);
    }

    @Test
    void deposit_ten_amount_with_account_of_zero_balance() {
        //Given
        Amount amount = new Amount(BigDecimal.TEN);
        Balance balance = new Balance(BigDecimal.ZERO);
        Account account = new Account(balance);

        //When
        account.deposit(amount);

        //Then
        assertThat(account.getBalance()).isEqualTo(new Balance(BigDecimal.TEN));
    }

    @Test
    void withdrawal_ten_amount_with_account_of_ten_balance() {
        //Given
        Balance  balance = new Balance(BigDecimal.TEN);
        Account account = new Account(balance);
        Amount amount = new Amount(BigDecimal.TEN);

        //When
        account.withdrawal(amount);

        //Then
        assertThat(account.getBalance()).isEqualTo(new Balance(BigDecimal.ZERO));
    }

    @Test
    void withdrawal_ten_amount_with_account_of_zero_balance_should_throw_balanceNotSufficientException() {
        //Given
        Balance  balance = new Balance(BigDecimal.ZERO);
        Account account = new Account(balance);
        Amount amount = new Amount(BigDecimal.TEN);

        //When
        BalanceNotSufficientException balanceNotSufficientException = assertThrows(BalanceNotSufficientException.class, () -> {
            account.withdrawal(amount);
        });

        //Then
        assertThat(balanceNotSufficientException.getMessage()).isEqualTo("balance not sufficient");
    }

    @Test
    void create_account_should_initialize_statement_with_empty_operation_lines() {
        //Given
        Balance balance = new Balance(BigDecimal.ZERO);
        //When
        Account account = new Account(balance);
        //Then
        assertThat(account.getStatement().getOperationLines()).isEqualTo(new LinkedList<>());
    }


    @Test
    void deposit_ten_amount_should_add_this_operation() {
        //Given
        Amount amount = new Amount(BigDecimal.TEN);
        Balance balance = new Balance(BigDecimal.ZERO);
        Account account = new Account(balance, fixedClock);
        FakeOperationPrinter operationPrinter = new FakeOperationPrinter();


        //When
        account.deposit(amount);
        account.print(operationPrinter);

        //Then
       assertThat(operationPrinter.getOperationLines().get(0)).isEqualTo(new OperationLine(new Operation(DEPOSIT, amount, now(fixedClock)), balance.add(amount)));

    }

    @Test
    void withdrawal_ten_amount_should_add_this_operation() {
        //Given
        Amount amount = new Amount(BigDecimal.TEN);
        Balance balance = new Balance(BigDecimal.TEN);
        Account account = new Account(balance, fixedClock);
        FakeOperationPrinter operationPrinter = new FakeOperationPrinter();

        //When
        account.withdrawal(amount);
        account.print(operationPrinter);

        //Then
        assertThat(operationPrinter.getOperationLines().get(0)).isEqualTo(new OperationLine(new Operation(WITHDRAWAL, amount, now(fixedClock)), balance.substact(amount)));
    }

    @Test
    void deposit_ten_and_withdrawal_ten_should_save_two_operations() {
        //Given
        Amount amountDeposit = new Amount(BigDecimal.TEN);
        Amount amountWithdrawal = new Amount(BigDecimal.TEN);
        Balance balance = new Balance(BigDecimal.TEN);
        Account account = new Account(balance, fixedClock);
        FakeOperationPrinter operationPrinter = new FakeOperationPrinter();

        //When
        account.deposit(amountDeposit);
        account.withdrawal(amountWithdrawal);
        account.print(operationPrinter);

        //Then
        assertThat(operationPrinter.getOperationLines())
                .containsExactly(new OperationLine(new Operation(DEPOSIT, amountDeposit, now(fixedClock)), new Balance(new BigDecimal(20))),
                        new OperationLine(new Operation(WITHDRAWAL, amountWithdrawal, now(fixedClock)),new Balance(BigDecimal.TEN)));
    }

}
