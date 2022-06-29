package com.skynet.bank.Service;

import com.skynet.bank.domain.exception.AccountNotFoundException;
import com.skynet.bank.domain.exception.InsufficientAmountException;
import com.skynet.bank.domain.model.Account;
import com.skynet.bank.domain.service.BankService;
import com.skynet.bank.domain.service.impl.BankServiceImpl;
import com.skynet.bank.domain.spi.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.skynet.bank.domain.enums.OperationTypeEnum.DEPOSIT;
import static com.skynet.bank.domain.enums.OperationTypeEnum.WITHDRAWAL;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class BankServiceTest {

    private BankService bankService;
    private static final UUID ACCOUNT_CODE = randomUUID();
    private static final LocalDateTime NOW = now();

    @Mock
    private AccountRepository accountRepositoryMock;

    @BeforeEach
    void init() {
        openMocks(this);
        Account account = new Account(ACCOUNT_CODE, NOW, 20);
        when(this.accountRepositoryMock.findById(ACCOUNT_CODE)).thenReturn(Optional.of(account));
        this.bankService = new BankServiceImpl(accountRepositoryMock);
    }

    @Test
    void depositeAmount_withAccoundNotFound_shouldThrowException() {
        //Given
        double amount = 10;
        UUID codeAccount = randomUUID();

        //When
        AccountNotFoundException accountNotFoundException = assertThrows(AccountNotFoundException.class, () -> {
            this.bankService.deposite(codeAccount, amount);
        });

        //Then
        assertThat(accountNotFoundException.getMessage()).isEqualTo(String.format("This account %s not found !", codeAccount));
    }

    @Test
    void depositeAmount_withAccount_shouldDepositeAmount() {
        //Given
        double amount = 10;
        UUID codeAccount = ACCOUNT_CODE;


        //When
        Account account = this.bankService.deposite(codeAccount, amount);

        //Then
        assertThat(account.getBalance()).isEqualTo(20 + amount);
        assertThat(account.getOperations()).hasSize(1);
        assertThat(account.getOperations()).allMatch(op -> DEPOSIT.equals(op.getOperationTypeEnum()));
    }

    @Test
    void withdrawaltAmount_withAccount_shouldwithdrawalAmount() {
        //Given
        double amount = 10;
        UUID codeAccount = ACCOUNT_CODE;


        //When
        Account account = this.bankService.withdrawal(codeAccount, amount);

        //Then
        assertThat(account.getBalance()).isEqualTo(20 - amount);
        assertThat(account.getOperations()).hasSize(1);
        assertThat(account.getOperations()).allMatch(op -> WITHDRAWAL.equals(op.getOperationTypeEnum()));
    }

    @Test
    void withdrawaltAmount_withAmountLessThanZero_shouldThrowInsufficientAmountException() {
        //Given
        double amount = -10;
        UUID codeAccount = ACCOUNT_CODE;

        //When
        InsufficientAmountException exception = assertThrows(InsufficientAmountException.class, () -> {
            this.bankService.withdrawal(codeAccount, amount);
        });

        //Then
        assertThat(exception.getMessage()).isEqualTo("This amount must be greater than 0 !");
    }

    @Test
    void withdrawaltAmount_withAmountLessThanBalance_shouldThrowRuntimeException() {
        //Given
        double amount = 30;
        UUID codeAccount = ACCOUNT_CODE;

        //When
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            this.bankService.withdrawal(codeAccount, amount);
        });

        //Then
        assertThat(runtimeException.getMessage()).isEqualTo("You do not have enough money left to complete this operation !");
    }

    @Test
    void displayOperations_withAccount_shouldDisplayIt() {
        //Given
        double amountToDeposite = 10;
        double amountToWithDrawal = 5;
        UUID codeAccount = ACCOUNT_CODE;
        this.bankService.deposite(codeAccount, amountToDeposite);
        this.bankService.withdrawal(codeAccount, amountToWithDrawal);

        //When
        String display = this.bankService.displayOperations(codeAccount);

        //Then
        assertThat(display).contains("Operation | date | amount | balance", "WITHDRAWAL","5.0", "25.0", "DEPOSIT","10.0", "30.0");
    }

}
