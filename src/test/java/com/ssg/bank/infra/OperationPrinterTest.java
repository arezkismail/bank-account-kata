package com.ssg.bank.infra;

import com.ssg.bank.domain.Account;
import com.ssg.bank.domain.Amount;
import com.ssg.bank.domain.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

public class OperationPrinterTest {

    private static final LocalDateTime NOW = now();
    private static final String DATE_FORMAT = "dd/MM/YYYY HH:mm";
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private OperationPrinter operationPrinter;
    @BeforeEach
    void init() {
        this.operationPrinter = new OperationPrinter();
    }

    @Test
    void should_print_deposit_and_withdrawal_operations() throws Exception {
        //Given
        Amount amountDeposit = new Amount(BigDecimal.TEN);
        Amount amountWithdrawal = new Amount(BigDecimal.TEN);
        Balance balance = new Balance(BigDecimal.TEN);
        Account account = new Account(balance);
        account.deposit(amountDeposit);
        account.withdrawal(amountWithdrawal);

        String expectedOperationPrint = "operation  | date  | amount  | balance\n" +
                "DEPOSIT | %s | 10 | 20 \n" +
                "WITHDRAWAL | %s | 10 | 10";

        //When
        String actualOperationPrint = tapSystemOut(() -> {
            account.print(this.operationPrinter);
        });

        //Then
        assertThat(String.format(expectedOperationPrint, NOW.format(dateFormatter), NOW.format(dateFormatter)))
                .isEqualTo(actualOperationPrint.trim());
    }

}
