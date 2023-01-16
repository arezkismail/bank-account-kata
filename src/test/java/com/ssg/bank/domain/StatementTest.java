package com.ssg.bank.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedList;

import static com.ssg.bank.domain.enums.OperationType.DEPOSIT;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

public class StatementTest {

    @Test
    void create_statement_should_initialize_operation_line() {
        //When
        Statement statement = new Statement();

        //Then
        assertThat(statement.getOperationLines()).isEqualTo(new LinkedList<>());
    }

    @Test
    void add_operation_and_balance_should_add_this_in_operations_lines() {
        //Given
        Balance balance = new Balance(BigDecimal.TEN);
        Amount amount = new Amount(BigDecimal.TEN);
        Operation operation = new Operation(DEPOSIT, amount, now());
        Statement statement = new Statement();

        //When
        statement.addOperation(operation, balance);

        //Then
        assertThat(statement.getOperationLines()).containsExactly(new OperationLine(operation, balance));
    }


}
